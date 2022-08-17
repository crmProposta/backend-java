package com.proposta.crm.service.impl

import com.proposta.crm.data.utils.ProposalDTOData
import com.proposta.crm.entity.Phone
import com.proposta.crm.entity.Proposal
import com.proposta.crm.repository.*
import com.proposta.crm.service.ProposalService
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration.ValidationConfiguration
import org.springframework.context.annotation.Import
import javax.validation.Validation

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ProposalServiceImplTest {
        val proposal: Proposal = mockk()
        private val proposalRepository: ProposalRepository = mockk()
        private val authUserRepository: AuthUserRepository = mockk()
        private val proposalCustomerRepository: ProposalCustomerRepository = mockk()
        private val proposalPhoneRepository: ProposalPhoneRepository = mockk()
        private val proposalCustomerBankRepository: ProposalCustomerBankRepository = mockk()
        private val proposalService: ProposalService = ProposalServiceImpl(
            proposalRepository,
            authUserRepository,
            proposalCustomerRepository,
            proposalPhoneRepository,
            proposalCustomerBankRepository,
        )


        @Before
        fun init() {
            clearAllMocks()
        }

        @Test
        fun `should add proposal`() {
            addProposalMock()


            val result = proposalService.addProposal(ProposalDTOData.getProposalDTO())

            verify {
                proposalRepository.save(any())
            }
        }

        private fun addProposalMock() {
            every { proposalRepository.save(any()) } returns proposal
            every { authUserRepository.getReferenceById(any()) } returns mockk()
            every { proposal.customer } returns mockk()
            every { proposalCustomerRepository.save(any()) } returns proposal.customer
            every { proposalPhoneRepository.saveAll(any() as List<Phone>) } returns mockk()
            every { proposalCustomerBankRepository.save(any()) } returns mockk()
        }

        @Test
        fun `throw exception if cpf is invalid`() {

            addProposalMock()
            var proposal = ProposalDTOData.getProposalDTO()
            val proposalCustomer = proposal.customer.copy(cpf = "111111-111.11")
            proposal = proposal.copy(customer = proposalCustomer)
            proposalService.addProposal(proposal)
        }


    }