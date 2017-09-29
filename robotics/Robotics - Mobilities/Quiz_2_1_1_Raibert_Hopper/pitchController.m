function Tphi = pitchController(phi,phiDesired,dphi_dt)
kd_phi = 5;
kp_phi = 5;
Tphi= 0;

e = phiDesired - phi;

Tphi = kp_phi * e - kd_phi * dphi_dt;

