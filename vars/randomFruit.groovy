// returns a random fruit from a list of fruits

def call() {
    def fruits = ["apple", "banana", "cherry", "date", "elderberry", "fig", "grape", "honeydew", "kiwi", "lemon"]
    return fruits[new Random().nextInt(fruits.size())]
}
