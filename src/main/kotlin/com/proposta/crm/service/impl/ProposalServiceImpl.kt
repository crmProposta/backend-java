package com.proposta.crm.service.impl

import com.proposta.crm.dto.ProposalDTO
import com.proposta.crm.entity.Phone
import com.proposta.crm.entity.Proposal
import com.proposta.crm.entity.ProposalCustomer
import com.proposta.crm.entity.ProposalCustomerBank
import com.proposta.crm.exception.ControllerException
import com.proposta.crm.repository.*
import com.proposta.crm.service.ProposalService
import com.proposta.crm.validator.ProposalValidator
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class ProposalServiceImpl(
    private val proposalRepository: ProposalRepository,
    private val authUserRepository: AuthUserRepository,
    private val proposalCustomerRepository: ProposalCustomerRepository,
    private val proposalPhoneRepository: ProposalPhoneRepository,
    private val proposalCustomerBankRepository: ProposalCustomerBankRepository,
) : ProposalService {

    @Transactional
    override fun addProposal(proposalDTO: ProposalDTO) {
        val validator = ProposalValidator(proposalDTO)
        validator.validate()
        val proposalCustomer = saveCustomer(proposalDTO)
        saveProposal(proposalDTO, proposalCustomer)
    }

    private fun saveCustomer(proposalDTO: ProposalDTO): ProposalCustomer {
        val proposalCustomer = ProposalCustomer(
            null,
            proposalDTO.customer.cpf,
            proposalDTO.customer.address,
            proposalDTO.customer.birthDate,
            proposalDTO.customer.maritalStatus,
        )

        val proposalSaved = proposalCustomerRepository.save(proposalCustomer)

        val proposalPhones = proposalDTO.customer.contactPhones.map {
            Phone(null, it.phoneNumber, proposalCustomer)
        }.toList()
        proposalPhoneRepository.saveAll(proposalPhones)


        return proposalSaved
    }

    private fun saveProposal(proposalDTO: ProposalDTO, proposalCustomer: ProposalCustomer) {
        val proposal = Proposal(
            null,
            proposalCustomer,
            proposalDTO.proposalType,
            authUserRepository.getReferenceById(proposalDTO.registeredByUserId),
            authUserRepository.getReferenceById(proposalDTO.userSellerId),
            proposalDTO.transactionMediatorBank,
            proposalDTO.promoter,
            proposalDTO.saleSource,
            proposalDTO.proposalNumber
        )
        proposalRepository.save(proposal)

        val proposalCustomerBank = ProposalCustomerBank(
            proposal,
            proposalDTO.proposalCustomerBank.accountNumber,
            proposalDTO.proposalCustomerBank.bankName,
        )

        proposalCustomerBankRepository.save(proposalCustomerBank)
    }


}