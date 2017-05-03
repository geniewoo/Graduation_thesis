import tensorflow as tf
import numpy as np
tf.set_random_seed(777)  # for reproducibility

xy = np.loadtxt('data-04-zoo.csv', delimiter=',', dtype=np.float32)
x_data = xy[:, 0:-1]
y_data = xy[:, [-1]]

print(x_data.shape, y_data.shape)

nb_classes = 7  # 여섯가지 카테고리가 있다.
X = tf.placeholder(tf.float32, [None, 16])  # 16가지 분류기준
Y = tf.placeholder(tf.int32, [None, 1])   # 1가지 결과
Y_one_hot = tf.one_hot(Y, nb_classes)   # Y를 one_hot 값으로 바꾼다
print("one hot", Y_one_hot)
Y_one_hot = tf.reshape(Y_one_hot, [-1, nb_classes])     # 이 때 -1은 None 의미
print("reshape", Y_one_hot)

W = tf.Variable(tf.random_normal([16, nb_classes]), name='weight')
b = tf.Variable(tf.random_normal([nb_classes]), name='bias')

newName = tf.matmul(X, W) + b    # hypothesis 대신 tf.nn.softmax_cross_entropy_with_logits에 사용
hypothesis = tf.nn.softmax(newName)  # 확인용

cost_i = tf.nn.softmax_cross_entropy_with_logits(logits=newName, labels=Y_one_hot)

cost = tf.reduce_mean(cost_i)
optimizer = tf.train.GradientDescentOptimizer(learning_rate=0.1).minimize(cost)

prediction = tf.argmax(hypothesis, 1)   # 0~6까지의 값중 가장 가까운 값 반환
correction_prediction = tf.equal(prediction, tf.argmax(Y_one_hot, 1))
accuracy = tf.reduce_mean(tf.cast(correction_prediction, tf.float32))

with tf.Session() as sess:

    sess.run(tf.global_variables_initializer())

    for step in range(2001):
        sess.run(optimizer, feed_dict={X: x_data, Y: y_data})
        if step % 500 == 0:
            loss, acc = sess.run([cost, accuracy], feed_dict={X: x_data, Y: y_data})
            print("Step: {:5}\tLoss: {:.3f}\tAcc:{:.2%}".format(step, loss, acc))

    pred = sess.run(prediction, feed_dict={X: x_data})
    for p, y in zip(pred, y_data.flatten()):
        print("[{}] prediction: {} True Y: {}".format(p == int(y), p, int(y)))