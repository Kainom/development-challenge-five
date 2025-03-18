create schema if not exists med;


CREATE TABLE
    med.address (
        id BIGSERIAL PRIMARY KEY,
        street VARCHAR(120) NOT NULL,
        city VARCHAR(60) NOT NULL,
        state_ VARCHAR(60) NOT NULL,
        neighborhood VARCHAR(60) NOT NULL,
        zip_code VARCHAR(10) NOT NULL,
        number_house INTEGER NOT NULL,
        created_at TIMESTAMPTZ DEFAULT NOW(),
        updated_at TIMESTAMPTZ DEFAULT NOW()
    );

CREATE OR REPLACE FUNCTION update_the_field()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER update_address_trigger
BEFORE UPDATE ON address
FOR EACH ROW
EXECUTE FUNCTION update_the_field();