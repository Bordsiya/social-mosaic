create table if not exists public.friends_revision_draft (
    id varchar(255) not null,
    user_id varchar(255) not null,
    settings text not null,
    created_date timestamp with time zone not null,
    primary key(id, user_id)
);

create table if not exists public.img_themes_revision_draft (
    id varchar(255) primary key,
    friends_revision varchar(255) not null,
    settings text not null,
    created_date timestamp with time zone not null,
    primary key(id),
    foreign key (friends_revision) references friends_revision_draft(id)
);

create table if not exists public.group_themes_revision_draft (
    id varchar(255) primary key,
    friends_revision varchar(255) not null,
    settings text not null,
    created_date timestamp with time zone not null,
    primary key(id),
    foreign key (friends_revision) references friends_revision_draft(id)
);

create table if not exists public.img_similarity_revision_draft (
    id varchar(255) primary key,
    img_themes_revision varchar(255) not null,
    created_date timestamp with time zone not null,
    primary key(id),
    foreign key (img_themes_revision) references img_themes_revision_draft(id)
);

create table if not exists public.group_similarity_revision_draft (
    id varchar(255) primary key,
    group_themes_revision varchar(255) not null,
    created_date timestamp with time zone not null,
    primary key(id),
    foreign key (group_themes_revision) references group_themes_revision_draft(id)
);

create table if not exists public.draft_apply (
    id varchar(255) not null,
    user_id varchar(255) not null,
    friends_revision varchar(255) not null,
    img_themes_revision varchar(255) not null,
    group_themes_revision varchar(255) not null,
    img_similarity_revision varchar(255) not null,
    group_similarity_revision varchar(255) not null,
    created_date timestamp with time zone not null,
    primary key (id, user_id),
    foreign key (friends_revision) references friends_revision_draft(id),
    foreign key (img_themes_revision) references img_themes_revision_draft(id),
    foreign key (group_themes_revision) references group_themes_revision_draft(id),
    foreign key (img_similarity_revision) references img_similarity_revision_draft(id),
    foreign key (group_similarity_revision) references group_similarity_revision_draft(id)
);