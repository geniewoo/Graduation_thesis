import tensorflow as tf

x_data = [[73., 80., 75.], [93., 88., 93], [89., 91., 90.], [96., 98., 100.], [73., 66., 70.]]  #[5, 3]
y_data = [[152.], [185.], [180.], [196.], [142.]]   #[5, 1]

X = tf.placeholder(tf.float32, shape=[None, 3])
Y = tf.placeholder(tf.float32, shape=[None, 1])

W = tf.Variable(tf.random_normal([3, 1]), name = 'weight')
b = tf.Variable(tf.random_normal([1]), name = 'bias')

hypothesis = tf.matmul(X, W) + b #matrix multiply

cost = tf.reduce_mean(tf.square(hypothesis - Y))

optimizer = tf.train.GradientDescentOptimizer(learning_rate=5e-6)
train = optimizer.minimize(cost)

sess = tf.Session()

sess.run(tf.global_variables_initializer())

for step in range(28):
    cost_val, hy_val, _ = sess.run([cost, hypothesis, train], feed_dict={X: x_data, Y: y_data})
    if step % 3 == 0:
        print(step, "Cost: ", cost_val, "\nPrediction:\n", hy_val)