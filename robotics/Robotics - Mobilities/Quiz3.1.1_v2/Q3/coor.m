function F = coor(theta,x,y,L1,L2)
%COOR Summary of this function goes here
%   Detailed explanation goes here
theta1 = theta(1);
theta2 = theta(2);

F(1) = L1 * cos(theta1) + L2 * cos(theta1 + theta2) - x;
F(2) = L1 * sin(theta1) + L2 * sin(theta1 + theta2) - y;

end

