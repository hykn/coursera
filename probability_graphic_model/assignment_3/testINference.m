factors = ComputeSingletonFactors(allWords{1}, imageModel);
% allFactors = BuildOCRNetwork (Part2SampleImagesInput, imageModel, pairwiseModel, tripletList);
% temp = ComputeWordPredictions(allWords, imageModel, pairwiseModel, tripletList);
factors = ComputePairwiseFactors(Part2SampleImagesInput, pairwiseModel, 26);
factors = ComputeTripletFactors(Part3SampleImagesInput, tripletList, 26);

factors = ComputeSimilarityFactor (Part4SampleImagesInput, 26, 1, 2);
factors = ComputeAllSimilarityFactors(Part5SampleImagesInput, 26);
factors = ChooseTopSimilarityFactors(Part6SampleFactorsInput, 2);