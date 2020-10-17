rm(list=ls())

setwd("D:\\Monash\\FIT3077 SA")


chol = read.csv("cholesterol.csv")

chol

dt = sort(sample(nrow(chol), nrow(chol)*.7))
chol.train<-chol[dt,]
chol.test<-chol[-dt,]


library(rpart)
library(randomForest)
library(rpart.plot)

tree.chol = rpart(ActualValue ~ BMI+Triglycerides+Potassium+Glucose+Hemoglobin+Platelet+Age+Gender+Tobacco, chol.train,method="class")#,control =rpart.control(minsplit =1,minbucket=1, cp=0))

tree.chol


plot(tree.chol)
text(tree.chol, digits=3)

model = predict(tree.chol, chol.test, type = "class")

chol.test$predictedValues <- model

chol.test

correctValues = 0
totalValue = 0

for (i in 1:nrow(chol.test)){
  av = chol.test[i,"ActualValue"]
  pv = chol.test[i,"predictedValues"]
  if (!is.null(av) && !is.null(pv)){
    if (av == pv){
      correctValues = correctValues + 1
    }
    totalValue = totalValue + 1
  }
  
}
correctValues
totalValue

accuracy = correctValues/totalValue

accuracy




rpart.plot(tree.chol, type = 3, digits = 3)
