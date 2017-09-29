function [ desired_state ] = traj_generator(t_given, state, waypoints)
% TRAJ_GENERATOR: Generate the trajectory passing through all
% positions listed in the waypoints list
%
% NOTE: This function would be called with variable number of input arguments.
% During initialization, it will be called with arguments
% trajectory_generator([], [], waypoints) and later, while testing, it will be
% called with only t and state as arguments, so your code should be able to
% handle that. This can be done by checking the number of arguments to the
% function using the "nargin" variable, check the MATLAB documentation for more
% information.
%
% t,state: time and current state (same variable as "state" in controller)
% that you may use for computing desired_state
%
% waypoints: The 3xP matrix listing all the points you much visited in order
% along the generated trajectory
%
% desired_state: Contains all the information that is passed to the
% controller for generating inputs for the quadrotor
%
% It is suggested to use "persistent" variables to store the waypoints during
% the initialization call of trajectory_generator.


%% Example code:
% Note that this is an example of naive trajectory generator that simply moves
% the quadrotor along a stright line between each pair of consecutive waypoints
% using a constant velocity of 0.5 m/s. Note that this is only a sample, and you
% should write your own trajectory generator for the submission.

persistent  waypoints0 traj_time d0;
persistent Alpha;
persistent n power_max k;
persistent S T P

if nargin > 2
    waypoints0  = waypoints;
    d = waypoints(:,2:end) - waypoints(:,1:end-1);
    d0 = 2 * sqrt(d(1,:).^2 + d(2,:).^2 + d(3,:).^2);
    traj_time = [0, cumsum(d0)];

    S = traj_time;
    T = traj_time(2:end) - traj_time(1:end - 1);


    n = size(waypoints,2) - 1;
    power_max = 7;
    k = power_max + 1;

    syms t S_is1 T_i P;
    for kk = 1:k
        P(1,kk) = ((t - S_is1)/T_i) ^ (kk - 1);
    end

    for kk = 2:k
        P(kk,:) = diff(P(kk - 1,:),t);
    end

    A = zeros(k * n , k * n);
    B_X = zeros(k * n , 1);
    B_Y = zeros(k * n , 1);
    B_Z = zeros(k * n , 1);

    w_x = waypoints(1,:);
    w_y = waypoints(2,:);
    w_z = waypoints(3,:);


    % alpha matrix:
    % aph10 ; aph11 ; aph12 ; aph13 ; ... aph17 ; aph20 ; aph21 ; ... ; aphn0 ;
    % aphn1 ; ... aphn6; aphn7;


    % 2n points 
    % p_i(S_i-1) = w_i-1
    % p_i(S_i) = w_i

    for nn = 1:2:(2 * n - 1)

        n_index = (nn + 1)/2;
        alpha_index = (1:k) + k * (n_index - 1);

        % X
        A(nn,alpha_index) = subs(P(1,:),[t,S_is1,T_i],[S(n_index - 1 + 1),S(n_index - 1 + 1),T(n_index)]);
        A(nn + 1,alpha_index) = subs(P(1,:),[t,S_is1,T_i],[S(n_index + 1),S(n_index - 1 + 1),T(n_index)]);

        B_X(nn) = w_x(n_index - 1 + 1);
        B_X(nn + 1) = w_x(n_index + 1);

        B_Y(nn) = w_y(n_index - 1 + 1);
        B_Y(nn + 1) = w_y(n_index + 1);

        B_Z(nn) = w_z(n_index - 1 + 1);
        B_Z(nn + 1) = w_z(n_index + 1);

    end

    % 6 points
    % p_1(k)(S_0) = p_n(k)(S_0) = 0; k = 1,2,3

    nn = 2 * n + 1;
    for kk = 1:3
        n_index = 1;
        A(nn, 1:k) = subs(P(kk + 1,:),[t,S_is1,T_i],[S(n_index - 1 + 1),S(n_index - 1 + 1),T(n_index)]);
        B(nn) = 0;
        nn = nn + 1;

        n_index = n;
        A(nn,(1:k) + (n - 1) * k) = subs(P(kk + 1,:),[t,S_is1,T_i],[S(n_index + 1),S(n_index - 1 + 1),T(n_index)]);
        B(nn) = 0;

        nn = nn + 1;
    end


    % 6n - 6 points
    % p_i(k)(S_i) = p_i+1(k)(S_i) k = 1,2,..6

    nn = 2 * n + 7;
    for n_index = 1:n - 1
        alpha_index = (1:k) + k * (n_index - 1);
        alpha_index_nia1 = (1:k) + k * n_index;
        for kk = 1:6
            A(nn,alpha_index) = subs(P(kk + 1,:),[t,S_is1,T_i],[S(n_index + 1),S(n_index - 1 + 1),T(n_index)]);
            A(nn,alpha_index_nia1) = - subs(P(kk + 1,:),[t,S_is1,T_i],[S(n_index + 1),S(n_index + 1),T(n_index + 1)]);

            B(nn) = 0;

            nn = nn + 1;
        end
    end


    Alpha = inv(A) * [B_X,B_Y,B_Z];
    
    
   
else
%     t_given
    if(t_given > traj_time(end))
        t_given = traj_time(end);
    end
    if t_given == 0
        desired_state.pos = zeros(3,1);
        desired_state.vel = zeros(3,1);
        desired_state.acc = zeros(3,1);
        desired_state.yaw = 0;
        desired_state.yawdot = 0;
    else
        t_index = find(S >= t_given,1);
        t_index = t_index - 1;
        
        alpha_index = (1:k) + k * (t_index - 1);
        
        alpha_select = Alpha(alpha_index,:);
%         des_pos = subs(P(1,:),[t,S_is1,T_i],[t_given,S(t_index - 1 + 1),T(t_index)]) * alpha_select;
%         des_vel = subs(P(2,:),[t,S_is1,T_i],[t_given,S(t_index - 1 + 1),T(t_index)]) * alpha_select;
%         des_acc = subs(P(3,:),[t,S_is1,T_i],[t_given,S(t_index - 1 + 1),T(t_index)]) * alpha_select;
        
        t = t_given;
        S_is1 = S(t_index - 1 + 1);
        T_i = T(t_index);
        des_pos = [ 1, -(S_is1 - t)/T_i,    (S_is1 - t)^2/T_i^2,       -(S_is1 - t)^3/T_i^3,         (S_is1 - t)^4/T_i^4,        -(S_is1 - t)^5/T_i^5,          (S_is1 - t)^6/T_i^6,          -(S_is1 - t)^7/T_i^7] * alpha_select;
        des_vel = [ 0,            1/T_i, -(2*S_is1 - 2*t)/T_i^2,    (3*(S_is1 - t)^2)/T_i^3,    -(4*(S_is1 - t)^3)/T_i^4,     (5*(S_is1 - t)^4)/T_i^5,     -(6*(S_is1 - t)^5)/T_i^6,       (7*(S_is1 - t)^6)/T_i^7] * alpha_select;
        des_acc = [ 0,                0,                2/T_i^2, -(3*(2*S_is1 - 2*t))/T_i^3,    (12*(S_is1 - t)^2)/T_i^4,   -(20*(S_is1 - t)^3)/T_i^5,     (30*(S_is1 - t)^4)/T_i^6,     -(42*(S_is1 - t)^5)/T_i^7] * alpha_select;
        
%         des_pos = [2 0 0];
%         des_vel = [0 0 0];
%         des_acc = [0 0 0];
        
        desired_state.pos = des_pos';
        desired_state.vel = des_vel';
        desired_state.acc = des_acc';
        desired_state.yaw = 0;
        desired_state.yawdot = 0;
        

    end
    
end
%


%% Fill in your code here

% desired_state.pos = zeros(3,1);
% desired_state.vel = zeros(3,1);
% desired_state.acc = zeros(3,1);
% desired_state.yaw = 0;



end

