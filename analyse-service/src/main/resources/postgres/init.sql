create table if not exists public.analytic_revision
(
    id varchar(255) primary key,
    revision_id varchar(255) not null,
    created_date timestamp with time zone not null,
    settings text not null
);

CREATE UNIQUE INDEX IF NOT EXISTS analytic_revision_revision_id ON public.analytic_revision USING btree(revision_id);