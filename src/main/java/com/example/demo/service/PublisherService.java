package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Publisher;

public interface PublisherService {
	public List<Publisher> findAllPublishers();

	public Publisher findPublisherById(Long id);

	public void createPublisher(Publisher publisher);

	public void updatePublisher(Publisher publisher);

	public void deletePublisher(Long id);

}
