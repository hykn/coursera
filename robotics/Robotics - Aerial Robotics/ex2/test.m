Metrix = [0.3835 0.5710 0.9287;
          0.5710 0.5919 -0.4119;
          -1.3954 0.0217 1.1105];
      
% Metrix = [0.2120 0.7743 0.5963;
%           0.2120 -0.6321 0.7454;
%           0.9540 -0.0316 -0.2981];
% Metrix = [(0.5)^0.5 0 (0.5)^0.5;
%            0 1 0;
%           -(0.5)^0.5 0 (0.5)^0.5];
      
res1 = Metrix' * Metrix;
% res2 = Metrix * Metrix';

syms phi theta psi;

sigma = 1;
Z_rot = [cos(phi) -sin(phi) 0;
         sin(phi) cos(phi) 0;
         0 0 1];
     
Y_rot = [cos(theta) 0 sin(theta);
         0 1 0;
         -sin(theta) 0 cos(theta);];
 
ZYZ_rot = Z_rot * Y_rot * Z_rot;

Rot = [0.6927 -0.7146 0.0978 ; 0.7165 0.6973 0.0198;-0.0824 0.0564 0.995];
     
theta_v = sigma * acos(Rot(3,3))
phi_v = atan2(Rot(3,2)/sin(theta_v),-Rot(3,1)/sin(theta_v))
psi_v = atan2(Rot(2,3)/sin(theta_v),Rot(1,3)/sin(theta_v))


psi_4 = 2;
Rot_4 = [0.2919 0.643 -0.7081;
         -0.643 -0.4161 -0.643;
         -0.7081 0.643 0.2919];
     
 
u_hat_4 = 1/(2 * sin(psi_4)) * (Rot_4 - Rot_4');

psi_5 = pi;
Rot_5 = [-1/3 2/3 -2/3;
         2/3 -1/3 -2/3;
         -2/3 -2/3 -1/3];
u_hat_5 = 1/(2 * sin(psi_5)) * (Rot_5 - Rot_5')

Rot_6 = [0.675 -0.1724 0.7174;
        0.2474 0.9689 0;
        -0.6951 0.1775 0.6967];
omega_b = [0 -1 0.9689;
           1 0 -0.2474;
           -0.9689 0.2474 0];
       
Rot_6_dot = inv(Rot_6') * omega_b;
omega_s = Rot_6_dot * Rot_6'
