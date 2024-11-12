# LLM/AI Applications in The Land of Java: Virtual Threads, Vector Databases and Spring AI

## Agenda

1. [Introduction](#introduction)
2. [Virtual Threads](#virtual-threads)
3. [Vector Databases](#vector-databases)
4. [Spring AI](#spring-ai)

## Introduction

    There are two reasons why I decided to present all these three topics together:
        1. This talk is 30 minutes long including Q&A and I could not cover any of these topics in depth so I decided to at least give you an overview of all three topics.
        2. All three topics are more related to each other than meets the eye and it would have been very complicated and somewhat inefficient to develop an AI Application with Spring without this trio.

## Virtual Threads

    Requirements: 
    - Java 21

    //TODO talk about Continuations and Virtual Threads

## Vector Databases

    Requirements: 
    - Java 21 (to work with Virtual Threads)
    - A database that supports Vector Data Types (we will use PostgreSQL with the pgvector extension)

    We can use the pgvector extension to store and query vector data in PostgreSQL. The extension provides a new data type called vector that can store dense vectors of float4 values. The extension also provides a set of functions to work with vectors, such as calculating the cosine similarity between two vectors.

    Note 1: It's important to note that you can use vector databases without AI Vector databases are not only used for AI, they are also used for other purposes such as similarity search, recommendation systems, and more.

    Note 2. We will use Spring AI to interact with the Vector Database. Spring AI is a new project that provides support for building AI applications with Spring. It provides a set of abstractions and utilities to work with AI technologies, such as machine learning, deep learning, and vector databases.
     For this part of the talk we will focus only on the vector database support provided by Spring AI. We will go deeper about LLM/AI in the next part of the talk.

    //TODO Talk about Embeddings and Embedding Clients

## Spring AI

```