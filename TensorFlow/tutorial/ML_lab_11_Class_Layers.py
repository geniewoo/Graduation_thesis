import tensorflow as tf

#get MNIST
from tensorflow.examples.tutorials.mnist import input_data

class Model:
    def __init__(self, sess, name):
        self.sess = sess
        self.name = name
        self._build_net()

    def _build_net(self):
        nb_classes = 10

        with tf.variable_scope(self.name):
            self.keep_prob = tf.placeholder(tf.float32)
            self.X = tf.placeholder(tf.float32, [None, 784])
            X_img = tf.reshape(self.X, [-1, 28, 28, 1])
            self.Y = tf.placeholder(tf.float32, [None, nb_classes])

            conv1 = tf.layers.conv2d(inputs=X_img, filters=32, kernel_size=[3,3], padding="SAME", activation=tf.nn.relu)
            pool1 = tf.layers.max_pooling2d(inputs=conv1, pool_size=[2,2], padding="SAME", strides=2)

            conv2 = tf.layers.conv2d(inputs=pool1, filters=64, kernel_size=[3, 3], padding="SAME", activation=tf.nn.relu)
            pool2 = tf.layers.max_pooling2d(inputs=conv2, pool_size=[2, 2], padding="SAME", strides=2)

            conv3 = tf.layers.conv2d(inputs=pool2, filters=128, kernel_size=[3, 3], padding="SAME", activation=tf.nn.relu)
            pool3 = tf.layers.max_pooling2d(inputs=conv3, pool_size=[2, 2], padding="SAME", strides=2)
            flat = tf.reshape(pool3, [-1, 128 * 4 * 4])

            dense4 = tf.layers.dense(inputs=flat, units=625,  activation=tf.nn.relu)
            dropout4 = tf.layers.dropout(inputs=dense4, rate=self.keep_prob)

            hypothesis = tf.layers.dense(inputs=dropout4, units=nb_classes)

            self.cost = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits(logits=hypothesis, labels=self.Y))

            self.optimizer = tf.train.GradientDescentOptimizer(learning_rate=0.1).minimize(self.cost)

            is_correct = tf.equal(tf.arg_max(hypothesis, 1), tf.arg_max(self.Y, 1))
            self.accuracy = tf.reduce_mean(tf.cast(is_correct, tf.float32))

    def predict(self, x_test, keep_prop=1.0):
        return self.sess.run(self.logits, feed_dict={self.X: x_test, self.keep_prob: keep_prop})

    def get_accuracy(self, x_test, y_test, keep_prop=1.0):
        return self.sess.run(self.accuracy, feed_dict={self.X: x_test, self.Y: y_test, self.keep_prob: keep_prop})

    def train(self, x_data, y_data, keep_prop=0.7):
        return self.sess.run([self.cost, self.optimizer], feed_dict={self.X: x_data, self.Y: y_data, self.keep_prob: keep_prop})





mnist = input_data.read_data_sets("MNIST_data/", one_hot=True)

training_epochs = 100
batch_size = 100

sess = tf.Session()
m1 = Model(sess, "m1")
sess.run(tf.global_variables_initializer())



for epoch in range(training_epochs):
    avg_cost = 0
    total_batch = int(mnist.train.num_examples / batch_size)

    for i in range(total_batch):
        batch_xs, batch_ys = mnist.train.next_batch(batch_size)
        c, _ = m1.train(batch_xs, batch_ys)
        avg_cost += c / total_batch

    print('Epoch:', "%04d" % (epoch + 1), 'cost = ', '{:.9f}', format(avg_cost))

print("Accuracy: ", m1.get_accuracy(mnist.test.images, mnist.test.labels))

