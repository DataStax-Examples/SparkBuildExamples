from pyspark import SparkContext

if __name__ == "__main__":

    sc = SparkContext(appName="Datastax Python example")

    letters = ['d', 'a', 't', 'a', 's', 't', 'a', 'x']

    rdd = sc.parallelize(letters)

    upperCased = rdd.map(lambda letter: letter.upper()).collect()

    for letter in upperCased:
        print letter

    sc.stop()