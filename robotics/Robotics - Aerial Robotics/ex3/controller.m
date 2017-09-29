function [ u1, u2 ] = controller(~, state, des_state, params)
%CONTROLLER  Controller for the planar quadrotor
%
%   state: The current state of the robot with the following fields:
%   state.pos = [y; z], state.vel = [y_dot; z_dot], state.rot = [phi],
%   state.omega = [phi_dot]
%
%   des_state: The desired states are:
%   des_state.pos = [y; z], des_state.vel = [y_dot; z_dot], des_state.acc =
%   [y_ddot; z_ddot]
%
%   params: robot parameters

%   Using these current and desired states, you have to compute the desired
%   controls

% parame
% 
% u1 = 0;
% u2 = 0;

% FILL IN YOUR CODE HERE
%% General control
minF = params.minF;
maxF = params.maxF;

K_vy  = 8;
K_py = 0.1;
K_vz = 35;
K_pz = 200;
K_vphi = 35;
K_pphi = 4000;

% des_state.pos = [1;1];
% des_state.pos = [0;2];
% des_state.vel = [0;0];
% des_state.acc = [0;0];

% des_state.pos = state.pos;



phi_c = -1/params.gravity * (des_state.acc(1) + ...
                             K_vy * (des_state.vel(1) - state.vel(1)) + ...
                             K_py * (des_state.pos(1) - state.pos(1)));
phi_c_dot = 0;
phi_c_ddot = 0;
% phi_c_deg = -1;
% phi_c = phi_c_deg / 360 * 2 * pi;

u1 = params.mass * (params.gravity + des_state.acc(2) + ...
                    K_vz * (des_state.vel(2) - state.vel(2)) + ...
                    K_pz * (des_state.pos(2) - state.pos(2)));
u2 = params.Ixx * (phi_c_ddot + K_vphi * (phi_c_dot - state.omega) + ...
                   K_pphi * (phi_c - state.rot));

if u1 < 2 * minF
    u1 = 2 * minF;
else if u1 > 2 * maxF
        u1 = 2 * maxF;
    end
end

if u2 < minF - maxF
    u2 = minF - maxF;
else if u2 > maxF - minF
        u2 = maxF - minF;
    end
end
% u1 = F1 + F2          range: (2 * Fmin , 2*Fmax);
% u2 = L * (F1 - F2)    range: (Fmin - Fmax , Fmax - Fmin);
               
               
%% Hover control
% u1 = params.mass * (params.gravity - K_vz * z_dot + K_pz * (z_des - z));
% u2 = params.Ixx;


% des_state.vel
% u2 = 0;
% u1
% phi_c
end

