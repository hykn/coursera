function [endeff] = computeMiniForwardKinematics(rads1,rads2)

endeff = [0,0];

L1 = 1;
L2 = 2;


belta = rads1 - rads2;
temp = asin((L1/L2) * sin(belta/2));
alpha = belta/2 - temp;

theta2_s = pi + rads1 - alpha;

endeff = L1 * [cos(rads1),sin(rads1)] + L2 * [cos(theta2_s),sin(theta2_s)];
