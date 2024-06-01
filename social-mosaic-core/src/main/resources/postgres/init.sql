create table if not exists public.request
(
    id varchar(255) primary key,
    created_date timestamp with time zone not null,
    input text not null,
    modified_date timestamp with time zone,
    open char(1) not null,
    type varchar(255) not null,
    idempotency_token varchar(255) not null,
    parent_task varchar(255)
);

create table if not exists public.process_result
(
    id varchar(255) primary key,
    request_id varchar(255) not null,
    process_status varchar(255) not null,
    revision varchar(255),
    error_code varchar(255),
    error_message varchar(255),
    foreign key(request_id) references request(id) on delete cascade
);

create table if not exists public.task
(
    id varchar(255) primary key,
    parent_id varchar(255) not null,
    name varchar(255) not null,
    status varchar(255) not null,
    error varchar(255),
    error_description varchar(255),
    created_date timestamp with time zone not null,
    foreign key (parent_id) references request(parent_task) on delete cascade
);

CREATE UNIQUE INDEX IF NOT EXISTS request_unique_idempotency_token ON public.request USING btree(idempotency_token);
CREATE UNIQUE INDEX IF NOT EXISTS process_result_request_id ON public.process_result USING btree(request_id);
CREATE UNIQUE INDEX IF NOT EXISTS task_parent_id ON public.task USING btree(parent_id);