import pandas as pd
import matplotlib.pyplot as plt

# read the csv file to extract data

data = pd.read_csv('virtualBitMap.csv')
trueSpread = data['TrueSpread']
estimatedSpread = data['EstimatedSpread']

plt.scatter(trueSpread, estimatedSpread, s=50, alpha=0.6,
            edgecolor='black', linewidth=1)
plt.axline((0, 0), slope=1, color='red')
plt.title('Virtual Bit Map')
plt.xlabel('True Spread')
plt.ylabel('Estimated Spread')
plt.tight_layout()
plt.show()
