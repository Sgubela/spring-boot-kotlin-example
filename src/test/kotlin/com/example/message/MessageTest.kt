package com.example.message

import com.example.account.Account
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by wonwoo on 2016. 10. 27..
 */
@RunWith(SpringRunner::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
open class MessageTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var messageRepository: MessageRepository

    @Test
    open fun newMessageHas() {
        val account = this.entityManager.persist(
                Account("wonwoo", "passwd"))
        val message = Message("test message", account)
        val saved = this.messageRepository.save(message)
        assertThat(saved.message, `is`("test message"))
    }

    @Test
    open fun findAllMessages() {
        val account = this.entityManager.persist(
                Account("wonwoo", "passwd"))
        this.messageRepository.save(Message("test message", account))
        this.messageRepository.save(Message("ok test kotlin", account))
        val findMessages = this.messageRepository.findAll()
        assertThat(findMessages.size, `is`(8)) // init data +
    }

    @Test
    open fun findByMessage() {
        val account = this.entityManager.persist(
                Account("wonwoo", "passwd"))
        this.messageRepository.save(Message("test message", account))
        val findMessage = this.messageRepository.findById(7)
        assertThat(findMessage.get().message, `is`("test message")) // init data +
    }
}