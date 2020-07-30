
import numpy as np

weights = [0.125, 0.05, 0.25, 0.05, 0.375, 0.05, 0.25, 0.05, 0.625, 0.05, 0.25, 0.05, 0.875, 0.05, 0.25, 0.05, 0.125, 0.05, 0.75, 0.05, 0.375, 0.05, 0.75, 0.05, 0.625, 0.05, 0.75, 0.05, 0.875, 0.05, 0.75, 0.05, 2.0, 0.0, 0.0, 2.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 2.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0]


class LayerTypeOne:
    
    def __init__(self, numOfNeuronsInPrevLayer, numOfNeuronsInThisLayer, offsets):
        self.numOfNeuronsInPrevLayer = numOfNeuronsInPrevLayer
        self.numOfNeuronsInThisLayer = numOfNeuronsInThisLayer
        self.offsets = offsets
        
        self.output = np.empty((numOfNeuronsInThisLayer))
        
    def calcOutput(self, input):
        for i in range(self.numOfNeuronsInThisLayer):
            self.output[i] = 1 / (1 + self.calcSimilarityForNeuron(i, input))
        return self.output


    def calcSimilarityForNeuron(self, index, input):
        global weights
        similarity = 0.0
        for i in range(self.numOfNeuronsInPrevLayer):
            similarity += abs(input[i] - weights[self.offsets[index] + i*2]) / abs(weights[self.offsets[index] + i*2 + 1])
        return similarity
        
class LayerTypeTwo:
    
    def __init__(self, numOfNeuronsInPrevLayer, numOfNeuronsInThisLayer, offsets):
        self.numOfNeuronsInPrevLayer = numOfNeuronsInPrevLayer
        self.numOfNeuronsInThisLayer = numOfNeuronsInThisLayer
        self.offsets = offsets
		
        self.output = np.empty((numOfNeuronsInThisLayer))
		
    def calcOutput(self, input):
        for i in range(self.numOfNeuronsInThisLayer):
            self.output[i] = 1. / (1. + np.exp(-self.calcNetOfNeuron(i, input)))
        return self.output
        
    def calcNetOfNeuron(self, index, input):
        global weights
        net = 0.0
        for i in range(self.numOfNeuronsInPrevLayer):
            net += weights[self.offsets[index] + i] * input[i]
        net += weights[self.offsets[index] + self.numOfNeuronsInPrevLayer]
        return net

class NeuralNetwork:

    def __init__(self, neuronsPerLayer):
        
        self.numOfInputs = neuronsPerLayer[0]
        self.numOfOutputs = neuronsPerLayer[-1]
        self.layers = []
        
        offset = 0
        offsets = []
        for i in range(neuronsPerLayer[1]):
            offsets.append(offset)
            offset += 2*neuronsPerLayer[0]
        
        self.layers.append(LayerTypeOne(neuronsPerLayer[0], neuronsPerLayer[1], offsets))
        
        for i in range(2, len(neuronsPerLayer)):
            
            offsets = []
            for j in range(neuronsPerLayer[i]):
                offsets.append(offset)
                offset += neuronsPerLayer[i - 1] + 1
            
            self.layers.append(LayerTypeTwo(neuronsPerLayer[i - 1], neuronsPerLayer[i], offsets))
             
        self.numOfParams = offset

    def calcOutput(self, input):
        
        for layer in self.layers:
            output = layer.calcOutput(input)
            input = output
            
        return output
        
    def calcError(self, dataset):
        
        squareError = 0.0
        for (x, y) in dataset:
            output = self.calcOutput(x)
            for j in range(len(output)):
                squareError += (y[j] - output[j])**2
        return squareError / len(dataset)
        
    def predict2(self, dataset, params):
        global weights
        weights = params
        return self.predict(dataset)
        
    def predict(self, dataset):

        labels = np.empty((len(dataset)), dtype=float)
        for i, data in enumerate(dataset):
            output = self.calcOutput(data)
            #print(output, output.argmax())
            labels[i] = output.argmax()
            
        return labels






