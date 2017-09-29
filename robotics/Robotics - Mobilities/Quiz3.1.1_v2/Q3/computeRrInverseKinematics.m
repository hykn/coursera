function [rads1,rads2] = computeRrInverseKinematics(X,Y)

syms theta1 theta2 ;

rads1=0;
rads2=0;

L1 = 1;
L2 = 1;

% x0 = [0;0];
% options = optimset('Display','off');
% S = fsolve(@(theta)coor(theta,X,Y,L1,L2),[0,0],options);

% rads1 = S(1);
% rads2 = S(2);

eqn1 = L1 * cos(theta1) + L2 * cos(theta1 + theta2) == X;
eqn2 = L1 * sin(theta1) + L2 * sin(theta1 + theta2) == Y;
S = solve([eqn1,eqn2]);

rads1 = double(S.theta1(1,1));
rads2 = double(S.theta2(1,1));


