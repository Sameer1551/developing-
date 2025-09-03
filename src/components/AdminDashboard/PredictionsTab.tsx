import React from 'react';
import { Brain } from 'lucide-react';

interface Prediction {
  district: string;
  disease: string;
  riskLevel: 'High' | 'Medium' | 'Low';
  probability: string;
  timeframe: string;
  factors: string[];
}

interface PredictionsTabProps {
  predictions: Prediction[];
}

const PredictionsTab: React.FC<PredictionsTabProps> = ({ predictions }) => {
  const getRiskColor = (riskLevel: string) => {
    return riskLevel === 'High' ? 'text-red-700 bg-red-100' : 'text-amber-700 bg-amber-100';
  };

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h3 className="text-lg font-semibold text-gray-900">AI Health Predictions</h3>
        <button className="flex items-center space-x-2 px-4 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition-colors">
          <Brain className="h-4 w-4" />
          <span>Refresh Analysis</span>
        </button>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        {predictions.map((prediction, index) => (
          <div key={index} className="bg-white border-l-4 border-orange-400 rounded-lg p-6 shadow-sm hover:shadow-md transition-shadow">
            <div className="flex items-start justify-between mb-4">
              <div>
                <h4 className="text-lg font-semibold text-gray-900 mb-1">
                  Predicted {prediction.disease} Outbreak
                </h4>
                <p className="text-gray-600">{prediction.district} District</p>
              </div>
              <div className="text-right">
                <div className={`px-3 py-1 rounded-full text-sm font-medium ${getRiskColor(prediction.riskLevel)}`}>
                  {prediction.riskLevel} Risk
                </div>
                <p className="text-sm text-gray-500 mt-1">Probability: {prediction.probability}</p>
              </div>
            </div>

            <div className="space-y-4">
              <div>
                <p className="text-sm font-medium text-gray-700 mb-2">Timeframe</p>
                <p className="text-gray-900">{prediction.timeframe}</p>
              </div>
              
              <div>
                <p className="text-sm font-medium text-gray-700 mb-2">Contributing Factors</p>
                <ul className="text-sm text-gray-900 space-y-1">
                  {prediction.factors.map((factor, idx) => (
                    <li key={idx} className="flex items-center space-x-2">
                      <div className="w-1.5 h-1.5 bg-orange-400 rounded-full"></div>
                      <span>{factor}</span>
                    </li>
                  ))}
                </ul>
              </div>
            </div>

            <div className="mt-6 flex flex-wrap gap-3">
              <button className="bg-red-600 text-white px-4 py-2 rounded-lg hover:bg-red-700 transition-colors text-sm">
                Issue Alert
              </button>
              <button className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors text-sm">
                Deploy Resources
              </button>
              <button className="border border-gray-300 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-50 transition-colors text-sm">
                View Details
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default PredictionsTab;
