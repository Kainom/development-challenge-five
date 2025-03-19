create schema if not exists med;


CREATE TABLE
    med.patient (
        id BIGSERIAL PRIMARY KEY,
        first_name VARCHAR(60) NOT NULL,
        last_name VARCHAR(60) NOT NULL,
        email VARCHAR(100) UNIQUE NOT NULL,
        phone_number VARCHAR(13) NOT NULL,
        birth_day DATE NOT NULL,
        created_at TIMESTAMPTZ DEFAULT NOW(),
        updated_at TIMESTAMPTZ DEFAULT NOW(),
        address_id BIGSERIAL REFERENCES med.address(id)

    );

CREATE OR REPLACE FUNCTION update_the_field()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER update_patient_trigger
BEFORE UPDATE ON address
FOR EACH ROW
EXECUTE FUNCTION update_the_field();