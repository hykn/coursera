% Copyright (C) Daphne Koller, Stanford University, 2012

function [MEU OptimalDecisionRule] = OptimizeMEU( I )

  % Inputs: An influence diagram I with a single decision node and a single utility node.
  %         I.RandomFactors = list of factors for each random variable.  These are CPDs, with
  %              the child variable = D.var(1)
  %         I.DecisionFactors = factor for the decision node.
  %         I.UtilityFactors = list of factors representing conditional utilities.
  % Return value: the maximum expected utility of I and an optimal decision rule 
  % (represented again as a factor) that yields that expected utility.
  
  % We assume I has a single decision node.
  % You may assume that there is a unique optimal decision.
  D = I.DecisionFactors(1);

  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  %
  % YOUR CODE HERE...
  % 
  % Some other information that might be useful for some implementations
  % (note that there are multiple ways to implement this):
  % 1.  It is probably easiest to think of two cases - D has parents and D 
  %     has no parents.
  % 2.  You may find the Matlab/Octave function setdiff useful.
  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%    
  
  MEU = 0;
  OptimalDecisionRule = struct('var', [], 'card', [], 'val', []);
%   euf = CalculateExpectedUtilityFactor(I);
%   
%   OptimalDecisionRule.var = euf.var;
%   OptimalDecisionRule.card = euf.card;
%   OptimalDecisionRule.val = zeros(prod(euf.card), 1);
%   MEU = 0;
%   if length(I.RandomFactors) == 0
%       [MEU, index] = max(euf.val);
%       OptimalDecisionRule.val(index) = 1;
%   else
%       Assignment = IndexToAssignment([1:prod(OptimalDecisionRule.card)],OptimalDecisionRule.card);
%       for i = 1:prod(OptimalDecisionRule.card(2:end))
%           subAssignment = IndexToAssignment(i,OptimalDecisionRule.card(2:end));
%           index = [];
%           for j = 1:size(Assignment,1)
%               if all(Assignment(j,2:size(Assignment,2)) == subAssignment)
%                   index = [index;j];
%               end
%           end
%           Assignment = IndexToAssignment(index,OptimalDecisionRule.card);
%           value = GetValueOfAssignment(euf, Assignment);
%           [maxValue, maxIndex] = max(value);
%           OptimalDecisionRule.val(index) = 0;
%           OptimalDecisionRule.val(index(maxIndex)) = 1;
%           MEU = MEU + euf.val(index(maxIndex));
%       end
%     end
      
end
