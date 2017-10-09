import scipy.io as sc

mat = sc.loadmat('AADBinfo.mat')

print(mat.keys())

f = open("trainscore.txt", 'w')
# f.write((mat["trainNameList"]))
print(len(mat["trainNameList"][0]))
print(len(mat["trainScore"][0]))

trainNameList = mat["trainNameList"][0]
trainScore = mat["trainScore"][0].tolist()

for index in range(len(trainNameList)):
    f.write(trainNameList[index][0] + " " + str(trainScore[index]) + "\n")

testNameList = mat["testNameList"][0]
testScore = mat["testScore"][0].tolist()

for index in range(len(testNameList)):
    f.write(testNameList[index][0] + " " + str(testScore[index]) + "\n")
