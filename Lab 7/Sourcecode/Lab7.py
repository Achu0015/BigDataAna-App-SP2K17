from __future__ import print_function

import numpy
import pandas as pd
import tensorflow as tf
import matplotlib.pyplot as plt


from sklearn.datasets import load_boston
#importing the boston data from sklearn package
from sklearn.decomposition import PCA
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression

rng = numpy.random

#loading the Boston dataset into variable
dataset = load_boston()

print("Boston Dataset ")
print(dataset.keys())
print("")

print(dataset.data.shape)
print(dataset.target.shape)

df = pd.DataFrame(dataset.data, columns=dataset.feature_names)
df['target'] = dataset.target

print(df.describe())
print("")

data_reduced = PCA(n_components=1).fit_transform(dataset.data)

#defining the train and test models for X and Y variables

X_train, X_test, y_train, y_test = train_test_split(data_reduced, dataset.target)

print(X_train.shape, X_test.shape, y_train.shape, y_test.shape)

linear = LinearRegression().fit(X_train, y_train)
y_pred = linear.predict(X_test)

print("Mean squared for train data: %.5f" % linear.score(X_train, y_train))
print("Mean squared for test data:  %.5f" % linear.score(X_test, y_test))
print("")

print("Coefficients (Parameters theta_1..theta_n")
print(linear.coef_)
print("Y intercept (theta_0): %.2f" % linear.intercept_)
print("")

#printing the scatter plot for reduced and target dats
plt.scatter(data_reduced, dataset.target, c='r')
plt.plot(X_test, y_pred, '--k', c='b')
plt.show()