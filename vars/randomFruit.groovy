def call() {
    def words = ["apple", "banana", "cherry", "date", "elderberry", "fig", "grape", "honeydew", "kiwi", "lemon"]
    return words[new Random().nextInt(words.size())]
}
