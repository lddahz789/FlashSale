-- 秒杀存储过程
DELIMITER $$ -- 分号换行转换为$$
-- 定义存储过程
-- 参数: in表示输入参数;out 表示输出参数 可赋值
-- row_count() 返回上一条修改类型SQL的影响行数
-- row_count() 0:未修改数据 ;>0 修改的行数;<0 SQL错误/未执行修改SQL
CREATE PROCEDURE `flashsale`.`execute_sale`
(in v_flashsale_id bigint, in v_phone bigint,
in v_sale_time timestamp, out r_result int)
BEGIN
	DECLARE insert_count int DEFAULT 0;
	START TRANSACTION;
	insert ignore into order_ 
		(product_id,user_phone,create_time)
		values (v_flashsale_id,v_phone,v_sale_time);
	select row_count() into insert_count;
	IF (insert_count = 0) THEN
		ROLLBACK;
		set r_result = -1;
	ELSEIF(insert_count < 0) THEN
		set r_result = -2;
	ELSE
		update product
		set stock = stock - 1
		where product_id = product_id
		and end_time > v_sale_time
		and start_time < v_sale_time
		and stock > 0;
		select row_count() into insert_count;
		IF (insert_count = 0) THEN
		ROLLBACK;
		set r_result = 0;
		ELSEIF (insert_count < 0) THEN
			ROLLBACK;
			set r_result = -2;
			ELSE
				COMMIT;
				set r_result = 1;
		END IF;
	END IF;
END;
$$
--定义存储过程结束
 DELIMITER ;
 set @r_result = -3;
 --执行存储过程
 call execute_sale(1003,13917556899,now(),@r_result);
 -- 获取结果
 select @r_result;
 