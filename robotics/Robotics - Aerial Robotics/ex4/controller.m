function [F, M] = controller(t, state, des_state, params)
%CONTROLLER  Controller for the quadrotor
%
%   state: The current state of the robot with the following fields:
%   state.pos = [x; y; z], state.vel = [x_dot; y_dot; z_dot],
%   state.rot = [phi; theta; psi], state.omega = [p; q; r]
%
%   des_state: The desired states are:
%   des_state.pos = [x; y; z], des_state.vel = [x_dot; y_dot; z_dot],
%   des_state.acc = [x_ddot; y_ddot; z_ddot], des_state.yaw,
%   des_state.yawdot
%
%   params: robot parameters

%   Using these current and desired states, you have to compute the desired
%   controls

% state.pos;
% state.vel;
% state.rot;
% state.omega;

% des_state.pos;
% des_state.vel;
% des_state.acc;
% des_state.yaw;
% des_state.yawdot;

% params.mass;    % 0.18
% params.I;       3*3 
% params.invI;    
% params.gravity; % 9.81
% params.armlength;
% params.minF;
% params.maxF;

% =================== Your code goes here ===================

% Set desire position
% des_state.pos = [0;0;0];
% des_state.vel = [0;0;0];
% des_state.acc = [0;0;0];
% des_state.yaw = 0;
% des_state.yawdot = 0;

% des_state.pos

K_dz = 80;
K_pz = 500;

% Thrust
F = 0;

% Moment
M = zeros(3,1);
                
u1 = params.mass * (params.gravity + des_state.acc(3) + ...
    K_dz * (des_state.vel(3) - state.vel(3)) + ...
    K_pz * (des_state.pos(3) - state.pos(3)));

if u1 > 4 * params.maxF
    u1 = 4 * params.maxF;
else if u1 < 4 * params.minF 
        u1 = 4 * params.minF;
    end
end


K_pphi = 40000;
K_dphi = 350;
K_ptheta = 40000;
K_dtheta = 350;
K_ppsi = 1;
K_dpsi = 1;

u2 = zeros(3,1);

% des_state.pos = [1;1];
% des_state.pos = [0;2];
% des_state.vel = [0;0];
% des_state.acc = [0;0];

% des_state.pos = state.pos;
K_dx = 3;
K_px = 50;
K_dy = 3;
K_py = 50;

r_1ddot_desire = des_state.acc(1) + K_dx * (des_state.vel(1) - state.vel(1)) + K_px * (des_state.pos(1) - state.pos(1));
r_2ddot_desire = des_state.acc(2) + K_dy * (des_state.vel(2) - state.vel(2)) + K_py * (des_state.pos(2) - state.pos(2));

des_phi = 1/params.gravity * (r_1ddot_desire * sin(des_state.yaw) - r_2ddot_desire * cos(des_state.yaw));
des_theta = 1/params.gravity * (r_1ddot_desire * cos(des_state.yaw) + r_2ddot_desire * sin(des_state.yaw));
des_psi = des_state.yaw;

des_p = 0;
des_q = 0;
des_r = des_state.yawdot;

% t
if t > 4
    stop = 1;
end

u2(1) = params.I(1,1) * (K_pphi * (des_phi - state.rot(1)) + K_dphi * (des_p - state.omega(1)));
u2(2) = params.I(2,2) * (K_ptheta * (des_theta - state.rot(2)) + K_dtheta * (des_q - state.omega(2)));
u2(3) = params.I(3,3) * (K_ppsi * (des_psi - state.rot(3)) + K_dpsi * (des_r - state.omega(3)));

F = u1;
M = u2;

% des_phi
% des_state.pos(2)

% =================== Your code ends here ===================

end
