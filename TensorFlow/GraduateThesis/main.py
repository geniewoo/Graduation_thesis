import tensorflow as tf
from PIL import Image
import numpy as np

CROP_PATH = "cropImage\\"
SMALL_PATH = "smallImage\\"

f = open("score.txt", 'r')
TRAINING_DATA_RATE = 0.85
IMAGE_SIZE = 256
IMAGE_LAYER = 3
TRAINING_EPOCHS = 200
BATCH_NUM = 500
UP_DOWN_BASIS = 0.45

def getImageDatasByName(imageNameList):
    imageList = []
    for name in imageNameList:
        imageFile = Image.open(SMALL_PATH + name)
        imageRGBArray = np.array(imageFile)
        imageRGBList = imageRGBArray.tolist()
        imageList.append(imageRGBList)
    return imageList

def getImageUpDownByScore(imageScoreList):
    scoreList = []
    for score in imageScoreList:
        if score[0] >= UP_DOWN_BASIS:
            scoreList.append([1, 0])
        else:
            scoreList.append([0, 1])
    return scoreList

def getImageNameList():
    datasetNameArray = []
    datasetScoreArray = []
    i = 0
    while True:

        line = f.readline()
        if line == "":
            break
        line = line.split(" ")
        datasetNameArray.append(line[0])
        datasetScoreArray.append([float(line[1][:-1])])
        i += 1
    return datasetNameArray, datasetScoreArray

datasetNameArray, datasetScoreArray = getImageNameList()

trainingDatasetLen = int(len(datasetNameArray) * TRAINING_DATA_RATE)
testDatasetLen = len(datasetNameArray) - trainingDatasetLen

trainingDatasetNameArray = datasetNameArray[:trainingDatasetLen]
trainingDatasetScoreArray = datasetScoreArray[:trainingDatasetLen]
testDatasetNameArray = datasetNameArray[trainingDatasetLen:]
testDatasetScoreArray = datasetScoreArray[trainingDatasetLen:]

print(str(len(trainingDatasetNameArray)) + " " + str(len(testDatasetNameArray)))

##################################################################################################

X = tf.placeholder(tf.float32, [None, IMAGE_SIZE, IMAGE_SIZE, IMAGE_LAYER])
# X_img = tf.reshape(X, [-1, IMAGE_SIZE, IMAGE_SIZE, IMAGE_LAYER])
keep_prop = tf.placeholder(tf.float32)
Y = tf.placeholder(tf.float32, [None, 2])

W1 = tf.Variable(tf.random_normal([8, 8, 3, 48], stddev=0.01))   #필터크기x, 필터크기y, 색, 필터개수
L1 = tf.nn.conv2d(X, W1, strides=[1, 2, 2, 1], padding='VALID')  #
L1 = tf.nn.relu(L1)
L1 = tf.nn.max_pool(L1, ksize=[1, 4, 4, 1], strides=[1, 2, 2, 1], padding='VALID')

W2 = tf.Variable(tf.random_normal([4, 4, 48, 144], stddev=0.01))
L2 = tf.nn.conv2d(L1, W2, strides=[1, 1, 1, 1], padding='SAME')
L2 = tf.nn.relu(L2)
L2 = tf.nn.max_pool(L2, ksize=[1, 3, 3, 1], strides=[1, 2, 2, 1], padding='SAME')

W3_1 = tf.Variable(tf.random_normal([4, 4, 144, 192], stddev=0.01))
L3 = tf.nn.conv2d(L2, W3_1, strides=[1, 1, 1, 1], padding='SAME')
L3 = tf.nn.relu(L3)
W3_2 = tf.Variable(tf.random_normal([3, 3, 192, 240], stddev=0.01))
L3 = tf.nn.conv2d(L3, W3_2, strides=[1, 1, 1, 1], padding='SAME')
L3 = tf.nn.relu(L3)
L3 = tf.nn.max_pool(L3, ksize=[1, 3, 3, 1], strides=[1, 2, 2, 1], padding='SAME')

W4_1 = tf.Variable(tf.random_normal([3, 3, 240, 360], stddev=0.01))
L4 = tf.nn.conv2d(L3, W4_1, strides=[1, 1, 1, 1], padding='SAME')
L4 = tf.nn.relu(L4)
W4_2 = tf.Variable(tf.random_normal([3, 3, 360, 480], stddev=0.01))
L4 = tf.nn.conv2d(L4, W4_2, strides=[1, 1, 1, 1], padding='SAME')
L4 = tf.nn.relu(L4)
W4_3 = tf.Variable(tf.random_normal([3, 3, 480, 600], stddev=0.01))
L4 = tf.nn.conv2d(L4, W4_3, strides=[1, 1, 1, 1], padding='SAME')
L4 = tf.nn.relu(L4)
L4 = tf.nn.max_pool(L4, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')

W5_1 = tf.Variable(tf.random_normal([3, 3, 600, 600], stddev=0.01))
L5 = tf.nn.conv2d(L4, W5_1, strides=[1, 1, 1, 1], padding='SAME')
L5 = tf.nn.relu(L5)
W5_2 = tf.Variable(tf.random_normal([3, 3, 600, 600], stddev=0.01))
L5 = tf.nn.conv2d(L5, W5_2, strides=[1, 1, 1, 1], padding='SAME')
L5 = tf.nn.relu(L5)
L5 = tf.nn.avg_pool(L5, ksize=[1, 4, 4, 1], strides=[1, 4, 4, 1], padding='VALID')

L5 = tf.reshape(L5, [-1, 2400])

W6 = tf.get_variable("W6", shape=[2400, 600], initializer=tf.contrib.layers.xavier_initializer())
b6 = tf.Variable(tf.random_normal([600]))
L6 = tf.nn.relu(tf.matmul(L5, W6) + b6)
L6 = tf.nn.dropout(L6, keep_prob=keep_prop)

W7 = tf.get_variable("W7", shape=[600, 100], initializer=tf.contrib.layers.xavier_initializer())
b7 = tf.Variable(tf.random_normal([100]))
L7 = tf.nn.relu(tf.matmul(L6, W7) + b7)
L7 = tf.nn.dropout(L7, keep_prob=keep_prop)

W8 = tf.get_variable("W8", shape=[100, 2], initializer=tf.contrib.layers.xavier_initializer())
b8 = tf.Variable(tf.random_normal([2]))
hypothesis = tf.matmul(L7, W8) + b8

cost = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits(logits=hypothesis, labels=Y))

optimizer = tf.train.GradientDescentOptimizer(learning_rate=0.001)

train = optimizer.minimize(cost)

W8_hist = tf.summary.histogram("weights8", W8)
cost_sum = tf.summary.scalar("cost", cost)

is_correct = tf.equal(tf.arg_max(hypothesis, 1), tf.arg_max(Y, 1))
accuracy = tf.reduce_mean(tf.cast(is_correct, tf.float32))

###########################################################################################

with tf.Session() as sess:

    summary = tf.summary.merge_all()

    writer = tf.summary.FileWriter('./logs/rate001_small_045')
    writer.add_graph(sess.graph)

    sess.run(tf.global_variables_initializer())

    oneBatchNum = int(trainingDatasetLen / BATCH_NUM)
    for epoch in range(TRAINING_EPOCHS):
        avg_cost = 0

        for i in range(BATCH_NUM):
            batch_xs = getImageDatasByName(trainingDatasetNameArray[oneBatchNum * i:oneBatchNum * (i + 1)])
            batch_ys = getImageUpDownByScore(trainingDatasetScoreArray[oneBatchNum * i:oneBatchNum * (i + 1)])

            if i == 288 or i == 449:
                continue
            if np.array(batch_xs).shape != (oneBatchNum, 256, 256, 3):
                continue

            c, h, s, _ = sess.run([cost, hypothesis, summary, train], feed_dict={X: batch_xs, Y: batch_ys, keep_prop: 0.5})
            avg_cost += c / BATCH_NUM

            if i % 10 == 0:
                print(epoch, i, "Cost: ", c, " Prediction:")

                writer.add_summary(s, global_step=i + epoch * BATCH_NUM)

                for j in range(oneBatchNum):
                    print(h[j], batch_ys[j])

        print('Epoch:', "%04d" % (epoch + 1), 'cost = ', '{:.9f}', format(avg_cost))

    tf.train.write_graph(sess.graph_def, 'models/', 'small_045_001.pb', as_text=False)
    #

    test_batch_xs = getImageDatasByName(testDatasetNameArray)
    test_batch_ys = getImageUpDownByScore(testDatasetScoreArray)
    print("Accuracy: ", accuracy.eval(session=sess, feed_dict={X: test_batch_xs, Y: test_batch_ys, keep_prop: 1}))
