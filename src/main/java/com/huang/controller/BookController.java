package com.huang.controller;

import com.huang.pojo.Books;
import com.huang.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    //controller 调service层
    @Autowired
    @Qualifier("BookServiceImpl")
    private BookService bookService;

    //查询所有的书籍，并且返回到一个书籍的展示页面
    @RequestMapping("/allBook")
    public String list(Model model) {
        List<Books> list = bookService.queryAllBook();
        model.addAttribute("list", list);
        return "allBook";
    }

    //添加书籍
    @RequestMapping("/toAddBook")
    public String toAddPaper() {
        return "addBook";
    }

    @RequestMapping("/addBook")
    public String addPaper(Books books) {
        System.out.println(books);
        bookService.addBook(books);
        return "redirect:/book/allBook";
    }

    //修改书籍
    @RequestMapping("/toUpdateBook")
    public String toUpdateBook(Model model, int id) {
        Books books = bookService.queryBookById(id);
        System.out.println(books);
        model.addAttribute("book",books );
        return "updateBook";
    }

    @RequestMapping("/updateBook")
    public String updateBook(Model model, Books book) {
        System.out.println(book);
        bookService.updateBook(book);
        Books books = bookService.queryBookById(book.getBookID());
        model.addAttribute("books", books);
        return "redirect:/book/allBook";
    }

    //删除书籍
    @RequestMapping("/del/{bookId}")
    public String deleteBook(@PathVariable("bookId") int id) {
        bookService.deleteBookById(id);
        return "redirect:/book/allBook";
    }
    //查询书籍
    @RequestMapping("queryBook")
    public String queryBook(String queryBookName,Model model){
        Books books = bookService.queryBookBuName(queryBookName);
        ArrayList<Books> list = new ArrayList<Books>();
        list.add(books);
        if (books==null){
            list= (ArrayList<Books>) bookService.queryAllBook();
            model.addAttribute("error","未找到" );
        }

        model.addAttribute("list", list);
        return "allBook";

    }

}
