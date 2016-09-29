package testgrails

class BookController {
    static defaultAction = "list"

    def index() {}


    def list() {
        Book book = [id: 1, author: "asdf"];
    }
}
