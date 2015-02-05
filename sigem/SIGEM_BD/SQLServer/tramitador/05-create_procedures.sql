CREATE PROCEDURE spac_nextval
	@sequence varchar(100),
	@sequence_id INT OUTPUT
AS
-- return an error if sequence does not exist
-- so we will know if someone truncates the table
	SET @sequence_id = -1
	
	UPDATE SPAC_SQ_SEQUENCES
	SET    @sequence_id = sequence_id = sequence_id + 1
	WHERE  sequence_name = @sequence
RETURN @sequence_id