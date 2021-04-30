ALTER TABLE link_customer_item
DROP COLUMN order_id;
ALTER TABLE link_customer_item ADD COLUMN item_id  BIGINT REFERENCES item (id) ON DELETE SET NULL;
