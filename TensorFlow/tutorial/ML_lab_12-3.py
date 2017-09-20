import tensorflow as tf
import numpy as np

sample = "hi my name is sungwoo park how are you doing?"
idx2char = list(set(sample))
char2idx={c:i for i, c in enumerate(idx2char)}

sequence_length=len(sample) - 1
input_dim=len(char2idx)
hidden_size=len(char2idx)
num_classes = len(char2idx)
batch_size = 1

sample_idx = [char2idx[c] for c in sample]

x_data = [sample_idx[:-1]]
y_data = [sample_idx[1:]]

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
        result = sess.run(prediction, feed_dict={X: x_data})
        print(i, "loss:", l, "prediction: ", result, "true Y: ", y_data)

        result_str = [idx2char[c] for c in np.squeeze(result)]
        if i % 200 == 0:
            print("\tPrediction str: ", ''.join(result_str))