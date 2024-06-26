package com.example.demo.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Book;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookRepository bookRepository;

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Book> findAllBooks() {
		// TODO Auto-generated method stub
		return bookRepository.findAll();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Book> searchBooks(String keyword) {
		if(keyword != null) {
			return bookRepository.search(keyword);
		}
		return bookRepository.findAll();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Book findBookById(Long id) {
		return bookRepository.findById(id)
				.orElseThrow(() -> 
				new NotFoundException(String.format("Book not found with ID %d", id)));
	}

	@Override
	public void createBook(Book book) {
		bookRepository.save(book);
		
	}

	@Override
	public void updateBook(Book book) {
		bookRepository.save(book);
		
	}

	@Override
	public void deleteBook(Long id) {
		var book = bookRepository.findById(id)
				.orElseThrow(() -> 
				new NotFoundException(String.format("Book not found with ID %d", id)));

		bookRepository.deleteById(book.getId());
		
	}

	@Override
	public Page<Book> findPaginated(Pageable pageable) {
		var pageSize = pageable.getPageSize();
		var currentPage = pageable.getPageNumber();
		var startItem = currentPage * pageSize;
		List<Book> list;

		if (findAllBooks().size() < startItem) {
			list = Collections.emptyList();
		} else {
			var toIndex = Math.min(startItem + pageSize, findAllBooks().size());
			list = findAllBooks().subList(startItem, toIndex);
		}

		var bookPage = new PageImpl<Book>(list, PageRequest.of(currentPage, pageSize), findAllBooks().size());

		return bookPage;
	}
}
