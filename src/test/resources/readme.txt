
public interface ActiveAccountDetails {
    Long getAccountId();
    Long getCustomerId();
    Long getInstitutionId();
    Long getSourceAccountId();
    Long getPartyId();

    // Customer identifiers
    String getMid();
    String getTid();
}


@Query(value = """
            SELECT 
                a.id AS accountId,
                a.customer_id AS customerId,
                a.institution_id AS institutionId,
                a.source_account_id AS sourceAccountId,
                cn.party_id AS partyId,
                c.mid AS mid,
                c.tid AS tid
            FROM Account a
            JOIN Customer c ON a.customer_id = c.id
            JOIN Connection cn ON cn.customer_id = c.id AND cn.institution_id = a.institution_id
            WHERE c.tid = :tid AND cn.is_active = TRUE
            """, nativeQuery = true)
    List<ActiveAccountDetails> findAccountsWithPartyByCustomerTidForActiveConnection(@Param("tid") String tid);
	
	
	
    public List<ActiveAccountDetails> getAccountsByTid(String tid) {
        if (tid == null || tid.isBlank()) {
            throw new IllegalArgumentException("tid must not be null or blank");
        }
        return accountRepository.findAccountsWithPartyByCustomerTidForActiveConnection(tid);
    }