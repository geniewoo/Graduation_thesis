import tensorflow as tf

x_data = [1, 2, 3]  #first set data and put it later by feed_dict
y_data = [1, 2, 3]

W = tf.Variable(5.0, name = 'weight') #Only one Weight variable
X = tf.placeholder(tf.float32)
Y = tf.placeholder(tf.float32)

hypothesis = X * W

cost = tf.reduce_sum(tf.square(hypothesis - Y))

#optimizer = tf.train.GradientDescentOptimizer(learning_rate=0.0025) 이 두 줄 대신 직접 Gradient를 만듬
#train = optimizer.minimize(cost)

learning_rate = 0.2
gradient = tf.reduce_mean((W * X - Y) * X)
descent = W - learning_rate * gradient
update = W.assign(descent)

sess = tf.Session()

sess.run(tf.global_variables_initializer())

for step in range(100):
    sess.run(update, feed_dict={X: x_data, Y: y_data})
    print(step, sess.run(cost, feed_dict={X: x_data, Y: y_data}), sess.run(W))