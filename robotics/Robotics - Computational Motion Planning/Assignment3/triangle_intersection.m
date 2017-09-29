function flag = triangle_intersection(P1, P2)
% triangle_test : returns true if the triangles overlap and false otherwise

%%% All of your code should be between the two lines of stars.
% *******************************************************************

flag = false;

X_P1 = (P1(:,1))';
Y_P1 = (P1(:,2))';

X_P2 = (P2(:,1))';
Y_P2 = (P2(:,2))';

AB_IN = inpolygon(X_P1,Y_P1,X_P2,Y_P2);
BA_IN = inpolygon(X_P2,Y_P2,X_P1,Y_P1);

flag = (sum(AB_IN) + sum(BA_IN)> 0);
if flag == false
    XY1 = [P1(1:2,:),P1(2:end,:)];
    XY2 = [P2(1:2,:),P2(2:end,:)];

    out = lineSegmentIntersect(XY1,XY2);
    flag = (sum(out.intAdjacencyMatrix(:)) > 0);
end

% *******************************************************************
end