import sys,boto3
import matplotlib
matplotlib.use('pdf')
import matplotlib.pyplot as plt
from pyspark.sql.types import *
from random import random
from operator import add
from pyspark.sql import Row
from pyspark import SparkContext,SQLContext
from pyspark.sql import *
import psycopg2

'''
Run using -
spark-submit --jars /usr/share/aws/emr/ddb/lib/emr-ddb-hadoop.jar test.py or
os.environ['PYSPARK_SUBMIT_ARGS'] = '--jars /usr/share/aws/emr/ddb/lib/emr-ddb-hadoop.jar pyspark-shell'
'''
def copy_into_table(conn, table, columns, rows):
    cur = conn.cursor()
    sio = StringIO()
    # adapt_row is a custom method, differing from psyopg's adapt
    sio.write('\n'.join(adapt_row(row) for row in rows)
    sio.seek(0)
    cur.copy_from(sio, table, columns=columns)
    conn.commit()

def push_to_s3(filename,bucket):
    s3 = boto3.client('s3')
    s3.upload_file(filename,bucket,filename)


if __name__ == "__main__":
    sc = SparkContext(appName="CreditDynamo")
    conf = {
    "dynamodb.servicename": "dynamodb",
    "dynamodb.input.tableName": "credit_defaults_analytics",
    "dynamodb.output.tableName": "credit_defaults_analytics",
    "dynamodb.endpoint": "https://dynamodb.us-east-1.amazonaws.com",
    "dynamodb.regionid": "us-east-1",
    "mapred.output.format.class": "org.apache.hadoop.dynamodb.write.DynamoDBOutputFormat",
    "mapred.input.format.class": "org.apache.hadoop.dynamodb.read.DynamoDBInputFormat"
    }

    dynamoRDD = sc.hadoopRDD(
    inputFormatClass='org.apache.hadoop.dynamodb.read.DynamoDBInputFormat',
    keyClass='org.apache.hadoop.io.Text',
    valueClass='org.apache.hadoop.dynamodb.DynamoDBItemWritable',
    conf=conf
    )
    dynamoRowRDD = dynamoRDD.map(lambda x: Row(
    PAY_SEP=x[1]['item']['PAY_SEP']['s'],
    AGE=x[1]['item']['AGE']['s'],
    STATUS_SEP=x[1]['item']['STATUS_SEP']['s'],
    PAY_APR=x[1]['item']['PAY_APR']['s'],
    PAY_MAY=x[1]['item']['PAY_MAY']['s'],
    PAY_JUL=x[1]['item']['PAY_JUL']['s'],
    PAY_JUN=x[1]['item']['PAY_JUL']['s'],
    BILL_JUN=x[1]['item']['BILL_JUN']['s'],
    BILL_JUL=x[1]['item']['BILL_JUL']['s'],
    STATUS_MAY=x[1]['item']['STATUS_MAY']['s'],
    SEX=x[1]['item']['SEX']['s'],
    PAY_AUG=x[1]['item']['PAY_AUG']['s'],
    BILL_MAY=x[1]['item']['BILL_MAY']['s'],
    BILL_SEP=x[1]['item']['BILL_SEP']['s'],
    STATUS_JUL=x[1]['item']['STATUS_JUL']['s'],
    STATUS_JUN=x[1]['item']['STATUS_JUN']['s'],
    MARRIAGE=x[1]['item']['MARRIAGE']['s'],
    STATUS_AUG=x[1]['item']['STATUS_AUG']['s'],
    EDUCATION=x[1]['item']['EDUCATION']['s']
    ))
    sqlContext=SQLContext(sc)
    #Creates DataFrame from RDD
    dynamonewDF = sqlContext.createDataFrame(dynamoRowRDD)

    #Create a RDD only of boxPlots
    boxplotDF=dynamonewDF.select(dynamonewDF.BILL_MAY.astype(FloatType()),dynamonewDF.BILL_JUN.astype(FloatType()),dynamonewDF.BILL_JUL.astype(FloatType()),dynamonewDF.BILL_SEP.astype(FloatType()))
    boxplotDF.show()

    #Creating Hive table to sqoop import
    #boxplotDF.createOrReplaceTempView("sqoopTest")
    #sqlContext.sql("drop table if exists boxplot")
    #sqlContext.sql("create table boxplot as select * from sqoopTest")

    #Write rdd values to Post Gres Table boxplot
    conn = psycopg2.connect("dbname='secondDB' user='analytics' host='secondpostgres.chd2dbjdanxp.us-east-1.rds.amazonaws.com' port='5432' password='12345678'")


    '''boxplot_sep=boxplotDF.rdd.map(lambda row: row.BILL_SEP).sample(False,0.1).collect()
    boxplot_may=boxplotDF.rdd.map(lambda row: row.BILL_MAY).sample(False,0.1).collect()
    boxplot_jun=boxplotDF.rdd.map(lambda row: row.BILL_JUN).sample(False,0.1).collect()
    boxplot_jul=boxplotDF.rdd.map(lambda row: row.BILL_JUL).sample(False,0.1).collect()

    #Plot the image
    filename = 'boxplotmultiple.png'
    fig = plt.figure()
    ax = plt.axes()
    bp = plt.boxplot([boxplot_sep,boxplot_may,boxplot_jun,boxplot_jul],positions = [0.5,1.5,2.5,3.5])
    ax.set_xticks([0.5,1.5,2.5,3.5])
    ax.set_xticklabels(['SEP', 'MAY', 'JUNE','JULY'])
    plt.savefig(filename)

    #Upload image to business-insights bucket
    push_to_s3(filename,bucket)'''

    sc.stop()
