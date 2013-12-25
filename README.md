SentenceMaker
=============

Generates random sentences based on given text.
Makes a markov model based on sequences of 3 words (2 words if you pass in 2 in the constructor) in a sentence. Based on 2 words, it will choose the next word based on the markov model that the algorithm created. It will also printout the probability of each word when choosing the words (1.0 means that there was only one word choice available with the previous 2 words, 0.5 means that there were 2 words) and return the final sentence as a string.
