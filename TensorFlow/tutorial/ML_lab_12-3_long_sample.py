import tensorflow as tf
import numpy as np

sample = ("if you want to build a ship, don't drum up people together to "
        "collect wood and don't assign them tasks and work, but rather "
        "teach them to long for the endless immensity of the sea.")
idx2char = list(set(sample))
char2idx={c:i for i, c in enumerate(idx2char)}

sequence_length=10
input_dim=len(char2idx)
hidden_size=len(char2idx)
num_classes = len(char2idx)

sample_idx = [char2idx[c] for c in sample]

x_data = []
y_data = []
for i in range(0, len(sample) - sequence_length):
    x_str = sample[i:i + sequence_length]
    y_str = sample[i + 1: i + sequence_length + 1]
    print(i, x_str, '->', y_str)

    x = [char2idx[c] for c in x_str]
    y = [char2idx[c] for c in y_str]

    x_data.append(x)
    y_data.append(y)

batch_size = len(x_data)

X=tf.placeholder(tf.int32, [None, sequence_length])
Y=tf.placeholder(tf.int32, [None, sequence_length])

x_one_hot = tf.one_hot(X, num_classes)

cell = tf.contrib.rnn.BasicLSTMCell(num_units=hidden_size, state_is_tuple=True)
initial_state = cell.zero_state(batch_size, tf.float32)
outputs, _states = tf.nn.dynamic_rnn(cell, x_one_hot, initial_state=initial_state, dtype=tf.float32)

weights = tf.ones([batch_size, sequence_length])#모두 1로 만든다
sequence_loss = tf.contrib.seq2seq.sequence_loss(logits=outputs, targets=Y, weights=weights)
loss = tf.reduce_mean(sequence_loss)
train = tf.train.AdamOptimizer(learning_rate=0.1).minimize(loss)

prediction = tf.argmax(outputs, axis=2)

with tf.Session() as sess:
    sess.run(tf.global_variables_initializer())
    for i in range(2000):
        l, _ = sess.run([loss, train], feed_dict={X: x_data, Y: y_data})
        result = sess.run(outputs, feed_dict={X: x_data})

        if i % 200 == 0:
            for j, result in enumerate(result):
                index = np.argmax(result, axis=1)
                print(i, j, ''.join([idx2char[t] for t in index]), l)

    results = sess.run(outputs, feed_dict={X: x_data})
    for j, result in enumerate(results):
        index = np.argmax(result, axis=1)
        if j is 0:  # print all for the first result to make a sentence
            print(''.join([idx2char[t] for t in index]), end='')
        else:
            print(idx2char[index[-1]], end='')