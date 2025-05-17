package com.example.storage.consumer;

import com.example.entities.Consumer;

public interface ConsumerStorage {
	Consumer get(int ID) throws Exception;
	Consumer[] getAll() throws Exception;
	Consumer insert(String name) throws Exception;
	Consumer update(Consumer consumer) throws Exception;
}
