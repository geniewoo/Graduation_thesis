import numpy as np
import tensorflow as tf

x_data = np.array([[0, 0], [0, 1], [1, 0], [1, 1]], dtype=np.float32)
y_data = np.array([[0], [1], [1], [0]], dtype=np.float32)
X = tf.placeholder(tf.float32, name="input")
Y = tf.placeholder(tf.float32)

W1 = tf.Variable(tf.random_normal([2, 2]), name='weight1')
b1 = tf.Variable(tf.random_normal([2]), name='bias1')
layer1 = tf.sigmoid(tf.matmul(X, W1) + b1)

W2 = tf.Variable(tf.random_normal([2, 1]), name='weight2')
b2 = tf.Variable(tf.random_normal([1]), name='bias2')
hypothesis = tf.sigmoid(tf.matmul(layer1, W2, name="matmul_100") + b2)

cost = -tf.reduce_mean(Y * tf.log(hypothesis) + (1 - Y) * tf.log(1 - hypothesis), name="cost")
train = tf.train.GradientDescentOptimizer(learning_rate=0.1).minimize(cost)

predicted = tf.cast(hypothesis > 0.5, dtype=tf.float32)
accuracy = tf.reduce_mean(tf.cast(tf.equal(predicted, Y), dtype=tf.float32))

W2_hist = tf.summary.histogram("weights2", W2)
cost_summ = tf.summary.scalar("cost", cost)

summary = tf.summary.merge_all()

with tf.Session() as sess:

    writer = tf.summary.FileWriter('./logs')
    writer.add_graph(sess.graph)

    sess.run(tf.global_variables_initializer())
    for step in range(100):
        s, _ = sess.run([summary, train], feed_dict={X: x_data, Y: y_data})
        writer.add_summary(s, global_step=step)
        if step % 100 == 0:
            print(step, sess.run(cost, feed_dict={X: x_data, Y: y_data}), sess.run([W1, W2, b1, b2]))


    h, c, a = sess.run([hypothesis, predicted, accuracy], feed_dict={X: x_data, Y: y_data})

    tf.train.write_graph(sess.graph_def, 'models/', 'test.pb', as_text=False)
    tf.train.write_graph(sess.graph_def, 'models/', 'test_text_1.pb', as_text=True)

    print("\nHypothesis: ", h, "\nCorrect: ", c, "\nAccuracy: ", a)