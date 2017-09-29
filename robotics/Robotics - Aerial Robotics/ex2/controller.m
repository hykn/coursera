function [ u ] = pd_controller(~, s, s_des, params)
%PD_CONTROLLER  PD controller for the height
%
%   s: 2x1 vector containing the current state [z; v_z]
%   s_des: 2x1 vector containing desired state [z; v_z]
%   params: robot parameters

u = 0;

% FILL IN YOUR CODE HERE

K_p = 60;
K_v = 8;

e = s_des(1) - s(1);
e_des = s_des(2) - s(2);

u = params.mass * (s_des(2) + K_p * e + K_v * e_des + params.gravity);

if u < params.u_min
    u = params.u_min;
end

if u > params.u_max
    u = params.u_max;
end


end

