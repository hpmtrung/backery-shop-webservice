USE [master]
GO
/****** Object:  Database [BAKERY_SHOP]    Script Date: 27/04/2022 23:15:30 ******/
ALTER DATABASE [BAKERY_SHOP] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [BAKERY_SHOP].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [BAKERY_SHOP] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET ARITHABORT OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [BAKERY_SHOP] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [BAKERY_SHOP] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET  ENABLE_BROKER 
GO
ALTER DATABASE [BAKERY_SHOP] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [BAKERY_SHOP] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET RECOVERY FULL 
GO
ALTER DATABASE [BAKERY_SHOP] SET  MULTI_USER 
GO
ALTER DATABASE [BAKERY_SHOP] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [BAKERY_SHOP] SET DB_CHAINING OFF 
GO
ALTER DATABASE [BAKERY_SHOP] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [BAKERY_SHOP] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [BAKERY_SHOP] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'BAKERY_SHOP', N'ON'
GO
USE [BAKERY_SHOP]
GO
USE [BAKERY_SHOP]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[lastName] [nvarchar](40) NOT NULL,
	[firstName] [nvarchar](10) NOT NULL,
	[avatar] [varchar](100) NULL,
	[email] [varchar](255) NOT NULL,
	[phone] [varchar](12) NOT NULL,
	[address] [nvarchar](255) NULL,
	[activated] [bit] NOT NULL,
	[password] [varchar](64) NOT NULL,
	[activationKey] [varchar](20) NULL,
	[resetKey] [varchar](20) NULL,
	[resetDate] [datetime] NULL,
	[langKey] [char](2) NULL,
 CONSTRAINT [PK_UserInfo] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[AccountsAuthorities]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[AccountsAuthorities](
	[accountId] [int] NOT NULL,
	[authorityName] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[accountId] ASC,
	[authorityName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Authority]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Authority](
	[name] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CartDetail]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CartDetail](
	[accountId] [int] NOT NULL,
	[variantId] [int] NOT NULL,
	[quantity] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[accountId] ASC,
	[variantId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Category]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[image] [varchar](20) NOT NULL,
	[icon] [varchar](20) NOT NULL,
	[banner] [varchar](20) NOT NULL,
 CONSTRAINT [PK_Category] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DATABASECHANGELOG]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DATABASECHANGELOG](
	[ID] [nvarchar](255) NOT NULL,
	[AUTHOR] [nvarchar](255) NOT NULL,
	[FILENAME] [nvarchar](255) NOT NULL,
	[DATEEXECUTED] [datetime2](3) NOT NULL,
	[ORDEREXECUTED] [int] NOT NULL,
	[EXECTYPE] [nvarchar](10) NOT NULL,
	[MD5SUM] [nvarchar](35) NULL,
	[DESCRIPTION] [nvarchar](255) NULL,
	[COMMENTS] [nvarchar](255) NULL,
	[TAG] [nvarchar](255) NULL,
	[LIQUIBASE] [nvarchar](20) NULL,
	[CONTEXTS] [nvarchar](255) NULL,
	[LABELS] [nvarchar](255) NULL,
	[DEPLOYMENT_ID] [nvarchar](10) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DATABASECHANGELOGLOCK]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DATABASECHANGELOGLOCK](
	[ID] [int] NOT NULL,
	[LOCKED] [bit] NOT NULL,
	[LOCKGRANTED] [datetime2](3) NULL,
	[LOCKEDBY] [nvarchar](255) NULL,
 CONSTRAINT [PK_DATABASECHANGELOGLOCK] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Order]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[accountId] [int] NULL,
	[createdAt] [datetime2](0) NOT NULL,
	[total] [decimal](9, 0) NOT NULL,
	[address] [nvarchar](255) NOT NULL,
	[paymentMethod] [bit] NOT NULL,
	[statusId] [tinyint] NOT NULL,
	[note] [nvarchar](300) NULL,
	[profit] [decimal](9, 0) NOT NULL,
	[phone] [varchar](10) NOT NULL,
 CONSTRAINT [PK_Order] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderDetail]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetail](
	[orderId] [int] NOT NULL,
	[variantId] [int] NOT NULL,
	[quantity] [int] NOT NULL,
	[unitPrice] [decimal](9, 0) NOT NULL,
 CONSTRAINT [PK_OrderDetail] PRIMARY KEY CLUSTERED 
(
	[orderId] ASC,
	[variantId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Product]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[categoryId] [int] NOT NULL,
	[ingredients] [nvarchar](max) NULL,
	[allergens] [nvarchar](max) NULL,
	[available] [bit] NULL,
 CONSTRAINT [PK_Product] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ProductImage]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProductImage](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[productId] [int] NOT NULL,
	[imagePath] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK_ProductImage] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ProductType]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProductType](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_Size] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ProductVariant]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProductVariant](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[productId] [int] NOT NULL,
	[typeId] [int] NOT NULL,
	[cost] [decimal](9, 0) NOT NULL,
	[price] [decimal](9, 0) NOT NULL,
	[hot] [bit] NOT NULL,
	[available] [bit] NOT NULL,
 CONSTRAINT [PK_ProductVariant] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Status]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Status](
	[id] [tinyint] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](40) NOT NULL,
 CONSTRAINT [PK_Status] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[ProductWithMinPrice]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[ProductWithMinPrice] AS
SELECT productId, MIN(price) AS price
		FROM ProductVariant 
		WHERE available = 1 
		GROUP BY productId
GO
SET IDENTITY_INSERT [dbo].[Account] ON 

INSERT [dbo].[Account] ([id], [lastName], [firstName], [avatar], [email], [phone], [address], [activated], [password], [activationKey], [resetKey], [resetDate], [langKey]) VALUES (4, N'Hoàng', N'Nam', N'default.png', N'nam@gmail.com', N'0908765431', N'test2', 0, N'$2a$10$3xhA0j1veMblD1h2sQp.We/JavAlnQ4/BmQ2.WiaRc1zL4dqpzOvW', N'00000000000000000000', N'00000000000000000000', CAST(N'2020-01-02T00:00:00.000' AS DateTime), N'en')
INSERT [dbo].[Account] ([id], [lastName], [firstName], [avatar], [email], [phone], [address], [activated], [password], [activationKey], [resetKey], [resetDate], [langKey]) VALUES (6, N'Users', N'Websd', N'6.jpg', N'user@gmail.com', N'0323456784', N'New Address 2ssdf', 1, N'$2a$10$ju0iPt4/7U4dHJEUsKgUR.TxRlp4tTV6uS4SpLWsYN7Ze1IaUYVMG', N'00000000000000000000', N'TLu5yZ7I3Xh2STj70piy', CAST(N'2022-04-27T12:28:45.213' AS DateTime), N'en')
INSERT [dbo].[Account] ([id], [lastName], [firstName], [avatar], [email], [phone], [address], [activated], [password], [activationKey], [resetKey], [resetDate], [langKey]) VALUES (8, N'Nguyễn Thị', N'Bình', N'default.png', N'vbinh@gmail.com', N'0908765433', NULL, 1, N'$2a$10$3xhA0j1veMblD1h2sQp.We/JavAlnQ4/BmQ2.WiaRc1zL4dqpzOvW', N'00000000000000000000', N'00000000000000000000', CAST(N'2020-01-02T00:00:00.000' AS DateTime), N'en')
INSERT [dbo].[Account] ([id], [lastName], [firstName], [avatar], [email], [phone], [address], [activated], [password], [activationKey], [resetKey], [resetDate], [langKey]) VALUES (9, N'Nguyễn', N'Văn', N'default.png', N'nvan@gmail.com', N'0908765423', NULL, 1, N'$2a$10$3xhA0j1veMblD1h2sQp.We/JavAlnQ4/BmQ2.WiaRc1zL4dqpzOvW', N'00000000000000000000', N'00000000000000000000', CAST(N'2020-01-02T00:00:00.000' AS DateTime), N'en')
INSERT [dbo].[Account] ([id], [lastName], [firstName], [avatar], [email], [phone], [address], [activated], [password], [activationKey], [resetKey], [resetDate], [langKey]) VALUES (14, N'Me', N'websit', N'14.png', N'admin@gmail.com', N'0901234565', N'Admdm', 1, N'$2a$10$1vyqfymUP2WABWWLJ/4/YOQinC63/BDwRAv4yLLjy5K0nylxgEZsG', N'00000000000000000000', N'00000000000000000000', CAST(N'2020-01-02T00:00:00.000' AS DateTime), N'vi')
INSERT [dbo].[Account] ([id], [lastName], [firstName], [avatar], [email], [phone], [address], [activated], [password], [activationKey], [resetKey], [resetDate], [langKey]) VALUES (15, N'Huỳnh', N'Trung', N'15.png', N'mt10tmt@gmail.com', N'0223456789', N'', 1, N'$2a$10$Zid7UGV2IGM3n7yfepa1OuVyrsx8qH6DGewKx9HJYSqlm5Ay3gkYa', N'00000000000000000000', NULL, NULL, N'en')
INSERT [dbo].[Account] ([id], [lastName], [firstName], [avatar], [email], [phone], [address], [activated], [password], [activationKey], [resetKey], [resetDate], [langKey]) VALUES (21, N'Trần', N'Tiến', N'default.png', N'ht10@gmail.com', N'0987654322', NULL, 0, N'$2a$10$nl6qDjEg9nqo05zUL5JiceEgaI5r7C8nKJg3L6VpgHWoOukdpWJae', N'00000000000000000000', N'00000000000000000000', CAST(N'2020-01-02T00:00:00.000' AS DateTime), N'en')
INSERT [dbo].[Account] ([id], [lastName], [firstName], [avatar], [email], [phone], [address], [activated], [password], [activationKey], [resetKey], [resetDate], [langKey]) VALUES (2022, N'Nam', N'Lan', N'default.png', N'hao@mgi.com', N'1234578985', N'123sdf', 0, N'$2a$10$sFv3Znuf23WZAyw0E4.vpONe3CHzruwpvLVoLa9YhpvdVRc82jfdW', N'INL8T1GzVJ35tmPCyjhP', NULL, NULL, NULL)
INSERT [dbo].[Account] ([id], [lastName], [firstName], [avatar], [email], [phone], [address], [activated], [password], [activationKey], [resetKey], [resetDate], [langKey]) VALUES (2025, N'sdf', N'talat', N'default.png', N'trannam290400@gmail.com', N'1234567890', N'saf', 1, N'$2a$10$jC53ltL4d595eBMKh8aeQe4CN7D4MdNEqifJDpc/hY/cqyFqoUJIu', NULL, NULL, NULL, NULL)
INSERT [dbo].[Account] ([id], [lastName], [firstName], [avatar], [email], [phone], [address], [activated], [password], [activationKey], [resetKey], [resetDate], [langKey]) VALUES (2028, N'slka', N'sdfa', N'default.png', N'huynhtrung74210@gmail.com', N'1234567891', N'fadf', 1, N'$2a$10$vz1JU.OiU9DxhhX7vsPeHuGxA4J8Kr0JIf4hiI/ZorxAOfZ.5ArcO', NULL, NULL, NULL, NULL)
INSERT [dbo].[Account] ([id], [lastName], [firstName], [avatar], [email], [phone], [address], [activated], [password], [activationKey], [resetKey], [resetDate], [langKey]) VALUES (3022, N'Hoàng', N'Nam', N'default.png', N'nam23@gmail.com', N'0908765431', N'test2', 1, N'$2a$10$GsLuyuxZBJ9KvAQaiwoWx.wkJmXXVRfIVcrXoVLoiQ7ALJDO8Q2Q.', NULL, N'bz4hv8Mcz5owyQHYPLpG', CAST(N'2022-04-27T01:34:45.340' AS DateTime), N'en')
INSERT [dbo].[Account] ([id], [lastName], [firstName], [avatar], [email], [phone], [address], [activated], [password], [activationKey], [resetKey], [resetDate], [langKey]) VALUES (4022, N'Hoàng', N'Tâm', N'default.png', N'nam89@gmail.com', N'0908765431', N'test2', 1, N'$2a$10$acnFLKh.UvgYAyVBveVm3uzEdgA4wK3bNK18RCwCPLCTh.KaG1kiS', NULL, N'yBrtdU4meIOAULUGwNiG', CAST(N'2022-04-27T08:34:36.633' AS DateTime), N'en')
INSERT [dbo].[Account] ([id], [lastName], [firstName], [avatar], [email], [phone], [address], [activated], [password], [activationKey], [resetKey], [resetDate], [langKey]) VALUES (4023, N'Hoàng', N'Nguyễn', N'default.png', N'alalls@cmail.com', N'0908765431', N'test2', 1, N'$2a$10$Uq4BHceNpVWdSnSCHzBcY.4fc/bgk5nr6BSYWr4Xwgv2SU1YaW9di', NULL, N'8a8MPSbSIvadsvbkyOkB', CAST(N'2022-04-27T08:41:28.717' AS DateTime), N'vi')
INSERT [dbo].[Account] ([id], [lastName], [firstName], [avatar], [email], [phone], [address], [activated], [password], [activationKey], [resetKey], [resetDate], [langKey]) VALUES (4024, N'dfkd', N'Laala', NULL, N'none@cmail.vn', N'0125412142', N'123 aacc', 0, N'$2a$10$fwll5CtOsS1d7xIu3FwdU.FxcvRD7j/S7wDlAJEhaIaqFvpofx5Wq', N'XXvtRqh9FPfojh9uRtPu', NULL, NULL, NULL)
SET IDENTITY_INSERT [dbo].[Account] OFF
GO
INSERT [dbo].[AccountsAuthorities] ([accountId], [authorityName]) VALUES (4, N'ROLE_USER')
INSERT [dbo].[AccountsAuthorities] ([accountId], [authorityName]) VALUES (6, N'ROLE_USER')
INSERT [dbo].[AccountsAuthorities] ([accountId], [authorityName]) VALUES (14, N'ROLE_ADMIN')
INSERT [dbo].[AccountsAuthorities] ([accountId], [authorityName]) VALUES (14, N'ROLE_USER')
INSERT [dbo].[AccountsAuthorities] ([accountId], [authorityName]) VALUES (15, N'ROLE_USER')
INSERT [dbo].[AccountsAuthorities] ([accountId], [authorityName]) VALUES (2022, N'ROLE_USER')
INSERT [dbo].[AccountsAuthorities] ([accountId], [authorityName]) VALUES (2025, N'ROLE_USER')
INSERT [dbo].[AccountsAuthorities] ([accountId], [authorityName]) VALUES (2028, N'ROLE_USER')
INSERT [dbo].[AccountsAuthorities] ([accountId], [authorityName]) VALUES (4022, N'ROLE_USER')
INSERT [dbo].[AccountsAuthorities] ([accountId], [authorityName]) VALUES (4023, N'ROLE_ADMIN')
INSERT [dbo].[AccountsAuthorities] ([accountId], [authorityName]) VALUES (4023, N'ROLE_USER')
INSERT [dbo].[AccountsAuthorities] ([accountId], [authorityName]) VALUES (4024, N'ROLE_USER')
GO
INSERT [dbo].[Authority] ([name]) VALUES (N'ROLE_ADMIN')
INSERT [dbo].[Authority] ([name]) VALUES (N'ROLE_USER')
GO
SET IDENTITY_INSERT [dbo].[Category] ON 

INSERT [dbo].[Category] ([id], [name], [image], [icon], [banner]) VALUES (1, N'Cupcake', N'1.jpg', N'1-icon.png', N'1-banner.png')
INSERT [dbo].[Category] ([id], [name], [image], [icon], [banner]) VALUES (2, N'Cheesecake', N'2.jpg', N'2-icon.png', N'2-banner.png')
INSERT [dbo].[Category] ([id], [name], [image], [icon], [banner]) VALUES (3, N'Icebox', N'3.jpg', N'3-icon.png', N'3-banner.png')
INSERT [dbo].[Category] ([id], [name], [image], [icon], [banner]) VALUES (4, N'Bánh kem truyền thống', N'4.jpg', N'4-icon.png', N'4-banner.png')
INSERT [dbo].[Category] ([id], [name], [image], [icon], [banner]) VALUES (5, N'Pudding Chuối', N'5.jpg', N'5-icon.png', N'5-banner.png')
INSERT [dbo].[Category] ([id], [name], [image], [icon], [banner]) VALUES (6, N'Brownie', N'6.jpg', N'6-icon.png', N'6-banner.png')
SET IDENTITY_INSERT [dbo].[Category] OFF
GO
INSERT [dbo].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS], [DEPLOYMENT_ID]) VALUES (N'00000000000000', N'jhipster', N'config/liquibase/changelog/00000000000000_initial_schema.xml', CAST(N'2022-02-12T11:34:45.9300000' AS DateTime2), 1, N'EXECUTED', N'8:b8c27d9dc8db18b5de87cdb8c38a416b', N'createSequence sequenceName=sequence_generator', N'', NULL, N'4.6.1', NULL, NULL, N'4665683340')
INSERT [dbo].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS], [DEPLOYMENT_ID]) VALUES (N'00000000000001', N'jhipster', N'config/liquibase/changelog/00000000000000_initial_schema.xml', CAST(N'2022-02-12T11:34:57.6430000' AS DateTime2), 2, N'EXECUTED', N'8:7f3f83889ee4de5b5f0320a15d0e91d3', N'createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...', N'', NULL, N'4.6.1', NULL, NULL, N'4665683340')
GO
INSERT [dbo].[DATABASECHANGELOGLOCK] ([ID], [LOCKED], [LOCKGRANTED], [LOCKEDBY]) VALUES (1, 0, NULL, NULL)
GO
SET IDENTITY_INSERT [dbo].[Order] ON 

INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (2, 4, CAST(N'2021-11-08T17:37:38.0000000' AS DateTime2), CAST(1 AS Decimal(9, 0)), N'ninh Thuan', 0, 4, N'This is note', CAST(0 AS Decimal(9, 0)), N'0123456789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (4, 4, CAST(N'2021-11-08T23:48:21.0000000' AS DateTime2), CAST(1 AS Decimal(9, 0)), N'ninh Thuan', 0, 3, N'This is note', CAST(0 AS Decimal(9, 0)), N'0123456789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (7, 14, CAST(N'2021-12-08T10:55:22.0000000' AS DateTime2), CAST(200000 AS Decimal(9, 0)), N'ddd, Phường Phúc Xá, Quận Ba Đình, Thành phố Hà Nội', 0, 4, N'', CAST(70000 AS Decimal(9, 0)), N'0123456789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (8, 14, CAST(N'2021-12-08T10:56:45.0000000' AS DateTime2), CAST(460000 AS Decimal(9, 0)), N'ddd, Phường Đồng Xuân, Quận Hoàn Kiếm, Thành phố Hà Nội', 0, 4, N'sss', CAST(130000 AS Decimal(9, 0)), N'0123456789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (9, 14, CAST(N'2021-12-10T00:58:04.0000000' AS DateTime2), CAST(200000 AS Decimal(9, 0)), N'3333, Xã Má Lé, Huyện Đồng Văn, Tỉnh Hà Giang', 0, 4, N'sss', CAST(70000 AS Decimal(9, 0)), N'0123456789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (10, 14, CAST(N'2021-12-10T00:58:40.0000000' AS DateTime2), CAST(220000 AS Decimal(9, 0)), N'ddd, Phường Quang Trung, Thành phố Hà Giang, Tỉnh Hà Giang', 0, 4, N'w', CAST(60000 AS Decimal(9, 0)), N'0123456789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (11, 14, CAST(N'2021-12-10T00:59:33.0000000' AS DateTime2), CAST(350000 AS Decimal(9, 0)), N'333222, Xã Lý Bôn, Huyện Bảo Lâm, Tỉnh Cao Bằng', 0, 4, N'2', CAST(80000 AS Decimal(9, 0)), N'0123456789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (12, 14, CAST(N'2021-12-10T01:01:52.0000000' AS DateTime2), CAST(760000 AS Decimal(9, 0)), N'sv dd, Phường Đồng Xuân, Quận Hoàn Kiếm, Thành phố Hà Nội', 0, 4, N'', CAST(190000 AS Decimal(9, 0)), N'0123456789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (13, 14, CAST(N'2021-12-10T01:03:10.0000000' AS DateTime2), CAST(250000 AS Decimal(9, 0)), N'333, Xã Hồng Việt, Huyện Hoà An, Tỉnh Cao Bằng', 0, 4, N'', CAST(90000 AS Decimal(9, 0)), N'0123456789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (14, 14, CAST(N'2021-12-10T02:22:54.0000000' AS DateTime2), CAST(240000 AS Decimal(9, 0)), N'v, Thị trấn Phó Bảng, Huyện Đồng Văn, Tỉnh Hà Giang', 0, 4, N'f', CAST(70000 AS Decimal(9, 0)), N'0123456789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (15, 14, CAST(N'2021-12-10T02:23:53.0000000' AS DateTime2), CAST(360000 AS Decimal(9, 0)), N'dd, Xã Lũng Cú, Huyện Đồng Văn, Tỉnh Hà Giang', 0, 2, N'ss', CAST(80000 AS Decimal(9, 0)), N'0123456789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (16, 14, CAST(N'2021-12-10T02:24:10.0000000' AS DateTime2), CAST(210000 AS Decimal(9, 0)), N'dd, Phường Đồng Xuân, Quận Hoàn Kiếm, Thành phố Hà Nội', 0, 2, N'ss', CAST(90000 AS Decimal(9, 0)), N'0123456789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (17, 14, CAST(N'2021-12-10T02:25:26.0000000' AS DateTime2), CAST(220000 AS Decimal(9, 0)), N'dd, Phường Đồng Xuân, Quận Hoàn Kiếm, Thành phố Hà Nội', 0, 3, N'ss', CAST(60000 AS Decimal(9, 0)), N'0123456789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (18, 21, CAST(N'2021-12-13T05:55:06.0000000' AS DateTime2), CAST(340000 AS Decimal(9, 0)), N'dsd, Phường Đồng Xuân, Quận Hoàn Kiếm, Thành phố Hà Nội', 0, 4, N'', CAST(120000 AS Decimal(9, 0)), N'0123456789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (33, 21, CAST(N'2022-03-27T14:26:26.0000000' AS DateTime2), CAST(460000 AS Decimal(9, 0)), N'333, Xã Hồng Việt, Huyện Hoà An, Tỉnh Cao Bằng', 1, 4, NULL, CAST(170000 AS Decimal(9, 0)), N'0123456789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (34, 21, CAST(N'2022-03-27T22:03:46.0000000' AS DateTime2), CAST(460000 AS Decimal(9, 0)), N'333, Xã Hồng Việt, Huyện Hoà An, Tỉnh Cao Bằng', 1, 4, NULL, CAST(170000 AS Decimal(9, 0)), N'0123456789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (1019, 6, CAST(N'2022-04-15T12:04:16.0000000' AS DateTime2), CAST(940000 AS Decimal(9, 0)), N'123 Hai Ba Trung', 1, 2, NULL, CAST(310000 AS Decimal(9, 0)), N'0123456789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (2019, NULL, CAST(N'2022-04-19T16:39:40.0000000' AS DateTime2), CAST(410000 AS Decimal(9, 0)), N'fasdf', 1, 4, N'sf', CAST(150000 AS Decimal(9, 0)), N'1244568789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (2020, NULL, CAST(N'2022-04-19T16:43:06.0000000' AS DateTime2), CAST(410000 AS Decimal(9, 0)), N'alsdkjf', 1, 1, N'co', CAST(150000 AS Decimal(9, 0)), N'1231564488')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (2021, NULL, CAST(N'2022-04-19T17:09:41.0000000' AS DateTime2), CAST(20000 AS Decimal(9, 0)), N'fas', 1, 1, N'4554', CAST(10000 AS Decimal(9, 0)), N'4546548798')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (2022, NULL, CAST(N'2022-04-19T17:16:06.0000000' AS DateTime2), CAST(260000 AS Decimal(9, 0)), N'sdfa', 1, 1, N'4544', CAST(100000 AS Decimal(9, 0)), N'7986464545')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (2023, 6, CAST(N'2022-04-19T17:29:47.0000000' AS DateTime2), CAST(960000 AS Decimal(9, 0)), N'New Address 2', 1, 1, N'', CAST(280000 AS Decimal(9, 0)), N'0323456789')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (2024, 6, CAST(N'2022-04-19T17:30:30.0000000' AS DateTime2), CAST(350000 AS Decimal(9, 0)), N'sfasf', 1, 1, N'safsf', CAST(105000 AS Decimal(9, 0)), N'0123456788')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (2025, NULL, CAST(N'2022-04-19T18:13:20.0000000' AS DateTime2), CAST(60000 AS Decimal(9, 0)), N'sdfkasj', 1, 1, N'', CAST(30000 AS Decimal(9, 0)), N'4658999812')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (2026, 6, CAST(N'2022-04-21T08:12:20.0000000' AS DateTime2), CAST(260000 AS Decimal(9, 0)), N'New Address 2ssdf', 1, 4, N'', CAST(100000 AS Decimal(9, 0)), N'0323456784')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (2027, 6, CAST(N'2022-04-21T08:38:06.0000000' AS DateTime2), CAST(60000 AS Decimal(9, 0)), N'New Address 2ssdf', 1, 1, N'', CAST(30000 AS Decimal(9, 0)), N'0323456784')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (3019, 6, CAST(N'2022-04-21T15:46:16.0000000' AS DateTime2), CAST(700000 AS Decimal(9, 0)), N'New Address 2ssdf', 1, 1, N'', CAST(260000 AS Decimal(9, 0)), N'0323456784')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (4019, 6, CAST(N'2022-04-27T13:39:30.0000000' AS DateTime2), CAST(400000 AS Decimal(9, 0)), N'123 Hai Ba Trung', 1, 3, N'khong', CAST(110000 AS Decimal(9, 0)), N'0122114521')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (4020, NULL, CAST(N'2022-04-27T14:49:03.0000000' AS DateTime2), CAST(300000 AS Decimal(9, 0)), N'123 Hai Ba Trung', 1, 4, N'khong', CAST(110000 AS Decimal(9, 0)), N'0122122551')
INSERT [dbo].[Order] ([id], [accountId], [createdAt], [total], [address], [paymentMethod], [statusId], [note], [profit], [phone]) VALUES (4021, 14, CAST(N'2022-04-27T15:51:24.0000000' AS DateTime2), CAST(605000 AS Decimal(9, 0)), N'Address v.2', 1, 1, N'', CAST(180000 AS Decimal(9, 0)), N'0901234561')
SET IDENTITY_INSERT [dbo].[Order] OFF
GO
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2, 2, 1, CAST(1 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (4, 2, 2222, CAST(22222 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (7, 2, 1, CAST(200000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (8, 22, 1, CAST(220000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (8, 24, 1, CAST(240000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (9, 2, 1, CAST(200000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (10, 22, 1, CAST(220000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (11, 47, 1, CAST(350000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (12, 60, 1, CAST(360000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (12, 63, 1, CAST(400000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (13, 28, 1, CAST(250000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (14, 24, 1, CAST(240000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (15, 60, 1, CAST(360000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (16, 67, 1, CAST(210000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (17, 22, 1, CAST(220000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (18, 2, 1, CAST(200000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (18, 5, 1, CAST(140000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (33, 2, 1, CAST(200000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (33, 8, 2, CAST(130000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (34, 2, 1, CAST(200000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (34, 8, 2, CAST(130000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (1019, 2, 1, CAST(200000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (1019, 4, 2, CAST(220000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (1019, 11, 2, CAST(150000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2019, 2, 1, CAST(200000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2019, 7, 1, CAST(40000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2019, 11, 1, CAST(150000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2019, 2093, 1, CAST(20000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2020, 2, 1, CAST(200000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2020, 7, 1, CAST(40000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2020, 11, 1, CAST(150000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2020, 2093, 1, CAST(20000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2021, 2093, 1, CAST(20000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2022, 2, 1, CAST(200000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2022, 7, 1, CAST(40000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2022, 2093, 1, CAST(20000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2023, 19, 4, CAST(220000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2023, 2093, 4, CAST(20000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2024, 11, 1, CAST(150000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2024, 14, 1, CAST(200000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2025, 7, 1, CAST(40000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2025, 2093, 1, CAST(20000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2026, 2, 1, CAST(200000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2026, 7, 1, CAST(40000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2026, 2093, 1, CAST(20000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2027, 7, 1, CAST(40000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (2027, 2093, 1, CAST(20000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (3019, 2, 3, CAST(200000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (3019, 2093, 5, CAST(20000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (4019, 14, 2, CAST(200000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (4020, 4, 1, CAST(220000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (4020, 7, 2, CAST(40000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (4021, 14, 1, CAST(200000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (4021, 15, 1, CAST(150000 AS Decimal(9, 0)))
INSERT [dbo].[OrderDetail] ([orderId], [variantId], [quantity], [unitPrice]) VALUES (4021, 23, 1, CAST(255000 AS Decimal(9, 0)))
GO
SET IDENTITY_INSERT [dbo].[Product] ON 

INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (1, N'Bánh Cupcake Hoa Hồng đỏsdfds', 4, N'Đường cát, Bột ca cao, Bột bánh, Baking Soda, Muối, Bơ, Trứng, Sữa, Kem chua, Chiết xuất Vanilla, Chất tạo màu thực phẩm, Bột mì, Dầu Canola, Bột nở, Đường bánh kẹo, Lòng trắng trứng gà', N'Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Glutensdf', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (2, N'Bánh Cupcake Confettidsfsfsdfsf', 3, N'Đường cát, Bột ca cao, Bột bánh, Baking Soda, Muối, Bơ, Trứng, Sữa, Kem chua, Chiết xuất Vanilla, Chất tạo màu thực phẩm, Bột mì, Dầu Canola, Bột ca cao, Bột nở, Hạt Sô-cô-la 65%, Đường bánh kẹo, Bông đường, Lòng trắng trứng, Chất tạo màu thực phẩm. Đường 1kg', N'Đường huyet', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (3, N'Bánh Cupcake Kiểu Nhật Tua', 5, N'Bột ca cao, Bột bánh, Baking Soda, Muối, Bơ, Trứng, Sữa, Kem chua, Chiết xuất Vanilla, Chất tạo màu thực phẩm, Bột mì, Dầu Canola, Bột ca cao, Bột nở, Hạt Sô-cô-la 65%, Đường bánh kẹo, Bông đường, Lòng trắng trứng', N'', 0)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (4, N'Bánh Cupcake Cổ điển hương Sô-cô-la', 2, N'Bột mì, Đường cát, Dầu Canola, Bột ca cao, Bột nở, Baking Soda, Muối, Sữa, Trứng, Chiết xuất Vanilla, Hạt Sô-cô-la 65%, Đường bánh kẹo, Bông đường', N'Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Gluten', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (5, N'Bánh Cupcake Halloween', 1, N'Đường cát, Bột ca cao, Bột bánh, Baking Soda, Muối, Bơ, Trứng, Sữa, Kem chua, Chiết xuất Vanilla, Chất tạo màu thực phẩm, Bột mì, Dầu Canola, Bột ca cao, Bột nở, Hạt Sô-cô-la 65%, Đường bánh kẹo, Bông đường, Lòng trắng trứng', N'Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Gluten', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (6, N'Bánh Cupcake Nhung đỏ', 1, N'Đường cát, Bột ca cao, Bột bánh, Baking Soda, Muối, Bơ, Trứng, Sữa, Kem chua, Giấm rượu táo, Chiết xuất Vanilla, Chất tạo màu thực phẩm, Bột mì', NULL, 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (7, N'Bánh Cupcake hương Vanilla', 1, N'Đường cát, Bột mì, Bột ca cao, Bột nở, Baking Soda, Muối, Trứng, Sữa, Chiết xuất Vanilla, Dầu Canola, Nước, Đường bánh kẹo, Sữa, Bông đường, Chất tạo màu thực phẩm', N'Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Gluten', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (8, N'Bánh Cupcake sinh nhật', 1, N'Đường cát, Bột ca cao, Bột bánh, Baking Soda, Muối, Bơ, Trứng, Sữa, Kem chua, Chiết xuất Vanilla, Chất tạo màu thực phẩm, Bột mì, Dầu Canola, Bột ca cao, Bột nở, Hạt Sô-cô-la 65%,', NULL, 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (9, N'Bánh Cheesecake Đậu Vanilla', 2, N'Kem phô mai, Đường cát, Chiết xuất Vanilla, Trứng, Nước chanh tây, Kem béo, Sữa, Bánh quy giòn Graham, Bơ', N'Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Gluten', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (10, N'Bánh Cheesecake chanh', 2, N'Kem phô mai, Đường cát, Chiết xuất Vanilla, Trứng, Nước chanh tây, Kem béo, Bánh quy giòn Graham, Bơ, Muối, Nước chanh ta', N'Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Gluten', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (11, N'Bánh Cheesecake Việt quất', 2, N'Kem phô mai, Đường cát, Chiết xuất Vanilla, Trứng, Nước chanh tây, Kem béo, Sữa, Bánh quy giòn Graham, Bơ, Việt quất, Vỏ chanh, Bột ngô, Nước, Đường nâu', N'Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Gluten', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (12, N'Bánh Cheesecake Caramel Hồ đào', 2, N'Kem phô mai, Đường cát, Chiết xuất Vanilla, Trứng, Nước chanh tây, Kem béo, Sữa, Bánh quy giòn Graham, Bơ, Đường Caramel, Hồ đào', N'Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Gluten, Nuts', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (14, N'Bánh Cheesecake Nhung đỏ', 2, N'Kem phô mai, Vanilla Sugar, Bột ca cao, Trứng, Chất tạo màu thực phẩm, Chiết xuất Vanilla, Muối, Kem chua, Kem béo, Bánh quế Sô-cô-la, Sô-cô-la bào', N'Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Gluten', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (15, N'Bánh kem vanilla', 4, N'Bột mì, Đường cát, Dầu Canola, Bột ca cao, Bột nở, Baking Soda, Muối, Sữa, Trứng, Chiết xuất Vanilla, Hạt Sô-cô-la 65%, Đường bánh kẹo, Bông đường', NULL, 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (16, N'Bánh kem cà rốt', 4, N'Bột mì, Cinnamon, Bột nở, Baking Soda, Muối, Trứng, Đường cát, Dầu Canola, Cà rốt, Dứa, nho khô, Dừa ngọt, Quả óc chó, Kem phô mai', N'Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Gluten, Quả hạch', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (17, N'Bánh kem Sô-cô-la', 4, N'Bột mì, Đường cát, Dầu Canola, Bột ca cao, Bột nở, Baking Soda, Muối, Sữa, Trứng, Chiết xuất Vanilla, Đường bánh kẹo, Chất tạo màu thực phẩm, Bông đường', N'Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Gluten', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (18, N'Bánh kem Confetti', 4, N'Bột bánh, Đường cát, Bơ, Bột nở, Baking Soda, Muối, Sữa, Kem chua, Lòng trắng trứng, Chiết xuất Vanilla, Đường bánh kẹo, Hạt Sô-cô-la 65%, Bông đường', NULL, 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (19, N'Bánh kem Hummingbird', 4, N'Bột mì, Baking Soda, Muối, Quế, Chuối, Trứng, Đường cát, Dầu Canola, Pineapple, Chiết xuất Vanilla, Pecans, Kem phô mai, Đường bánh kẹo', N'Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Gluten, Quả hạch', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (20, N'Bánh kem Nhung đỏ', 4, N'Đường cát, Bột ca cao, Bột bánh, Baking Soda, Muối, Bơ, Trứng, Sữa, Kem chua, Giấm rượu táo, Chiết xuất Vanilla, Chất tạo màu thực phẩm, Bột mì', NULL, 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (21, N'Bánh kem Kẹo ngô', 4, N'Bột bánh, Đường cát, Bơ, Bột nở, Baking Soda, Muối, Sữa, Kem chua, Lòng trắng trứng, Chiết xuất Vanilla, Đường bánh kẹo, Chất tạo màu thực phẩm, Bông đường', N'Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Gluten', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (22, N'Bánh Icebox Việt quất', 3, N'Bơ, Bột mì, Pecans, Kem béo, Kem phô mai, Đường bánh kẹo, Bột ngô, Việt quất, Đường cát, Đường nâu, Vỏ chanh', N'Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Gluten', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (23, N'Bánh Icebox Bơ đậu phụng', 3, N'Nilla Wafers, Bơ, Đường nâu, Kem phô mai, Đậu phộng Bơ, Đường bánh kẹo, Chiết xuất Vanilla, Kem béo, Đường Caramel, Cốc bơ đậu phộng, Đậu phộng', N'Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Gluten', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (24, N'Bánh Icebox Kem chuối', 3, N'Nilla Wafers Crumbs, Bơ, Kem béo, Chuối, Nước, Sữa đặc có đường, Jello Instant Vanilla Pudding, Đường cát', N'Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Gluten', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (25, N'Bánh Icebox Chanh', 3, N'Bánh quy giòn Graham Crumbs, Bơ, Đường nâu, Sữa đặc có đường, Trứng, Nước chanh ta, Vỏ chanh ta, Kem béo, Đường cát, Chiết xuất Vanilla', NULL, 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (26, N'Bánh Icebox Snicker', 3, N'Bánh quế Sô-cô-la Crumbs, Bơ, Đường nâu, Kem béo, Kem phô mai, Đường bánh kẹo, Bơ đậu phộng, Thanh Snicker', N'Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Gluten', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (27, N'Bánh Pudding chuối cổ điển', 5, N'Kem béo, Sữa đặc có đường, Pudding Vanilla ăn liền, Nước, Bánh quy wafer Nilla, Chuối', N'Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Gluten', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (30, N'Bánh Pudding chuối cổ điển không chứa gluten', 5, N'Kem béo, Sữa đặc có đường, Pudding Vanilla ăn liền, Nước, Bánh quy wafer GF, Chuối', N'Sữa, Trứng, Sản phẩm bơ sữa', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (31, N'Bánh Pudding chuối Sô-cô-la hạt phỉ', 5, N'Kem béo, Sữa đặc có đường, Pudding Vanilla ăn liền, Nước, Nilla Wafers, Chuối, Hạt phỉ', NULL, 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (32, N'Bánh Pudding chuối nhung đỏ', 5, N'Granulated Sugar, Cocoa Powder, Cake Flour, Baking Soda, Muối, Bơ, Trứng, Sữa, Kem chua, Giấm rượu táo, Chiết xuất Vanilla, Chất tạo màu thực phẩm, Kem béo, Sữa đặc có đường, Pudding Vanilla ăn liền, Chuối, Sô-cô-la bào', NULL, 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (33, N'Bánh Brownie Sô-cô-la', 6, N'Bột mì, Bột Ca cao, Bột nở, Muối, Đường cát, Chiết xuất Vanilla, Trứng, Bơ, Hạt Sô-cô-la ngọt vừa', NULL, 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (34, N'Bánh Brownie vụn Sô-cô-la và bơ cứng', 6, N'Bơ, Đường, Si-rô Ngô đen, Muối, Baking Soda, Chiết xuất Vanilla, 58% Sô-cô-la Couveture, Hồ đào', N'Sản phẩm bơ sữa, Quả hạch', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (35, N'Bánh Brownie vụn Bạc hà', 6, N'Kẹo bạc hà, Sô-cô-la Couverture trắng, 58% Sô-cô-la Couverture, Cacao Krispy', N'Sản phẩm bơ sữa, Chất Gluten', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (36, N'Bánh Brownie hương chanh', 6, N'Bột mì, Bơ, Đường bánh kẹo, Muối, Trứng, Đường cát, Vỏ chanh, Nước trái cây, Bột nở', N'
Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Gluten', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (38, N'Bánh Brownie hương Vanilla, Sô-cô-la', 6, N'Bột mì, Bột Ca cao, Bột nở, Muối, Đường cát, Chiết xuất Vanilla, Bơ, Trứng, Đường nâu, Hạt Sô-cô-la Callets 65%, Hạt Sô-cô-la ngọt vừa, Dừa ngọt, Quả óc chó, Sữa đặc có đường, Bánh quy giòn Graham', NULL, 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (39, N'Bánh Cupcake Carrie', 1, N'Bột làm bánh, Đường hạt, Bơ, Bột nở, Baking Soda, Muối, Sữa, Sữa chua, Lòng trắng trứng, Chiết xuất Vanilla, Đường bánh kẹo, Chất tạo màu thực phẩm, Bông đường', NULL, 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (41, N'Bánh Icebox Pudding Sô-cô-la', 3, N'
Đường cát, Bột ca cao, Bột bắp, Muối, Sữa, Sô-cô-la Callets 65%, Chiết xuất Vanilla, kem béo, Sô-cô-la bào', N'Lúa mì, Sữa, Trứng, Sữa, Chất Gluten
', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (42, N'Bánh Icebox bí ngô', 3, N'Vụn gừng, Vụn bánh quy giòn Graham, Bơ, Kem phô mai, Đường nâu, Đường bánh kẹo, Chiết xuất Vanilla, Quế, Đinh hương, Gừng, Nhục đậu khấu, Bí ngô, Sữa đặc có đường, Kem béo, Hồ đào, Lòng trắng trứng', N'Lúa mì, Sữa, Trứng, Sữa, Chất Gluten', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (43, N'Bánh kem kiểu Đức', 4, N'Bột mì , Sô-cô-la Callets 65%, Mảnh Sô-cô-la không đường, Baking Soda, Muối, Trứng, Bơ, Đường cát, Chiết xuất Vanilla, Sữa bơ, Sữa tươi, Dừa ngọt, Hồ đào', N'Lúa mì, Sữa, Trứng, Sữa, Gluten', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (48, N'Bánh Pitocat', 3, N'Bột mì, Bột Ca cao, Bột nở, Muối, Đường cát, Chiết xuất Vanilla, Bơ, Trứng, Đường nâu, Hạt Sô-cô-la Callets 65%, Hạt Sô-cô-la ngọt vừa, Dừa ngọt, Quả óc chó, Sữa đặc có đường, Bánh quy giòn Graham', NULL, 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (1044, N'Bánh code', 3, N'Muối, đường', NULL, 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (2044, N'Bánh dạo', 1, N'2 món', N'Không', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (3044, N'Bánh hành', 3, N'Muối, đường', N'Không', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (4044, N'Banh banh', 1, N'...', N'Không', 1)
INSERT [dbo].[Product] ([id], [name], [categoryId], [ingredients], [allergens], [available]) VALUES (4045, N'Banh banh 2', 1, N'...', N'Không', 1)
SET IDENTITY_INSERT [dbo].[Product] OFF
GO
SET IDENTITY_INSERT [dbo].[ProductImage] ON 

INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (1, 1, N'1637281686544.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (2, 1, N'1637281694203.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (3, 2, N'1637281701045.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (4, 39, N'1637281787513.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (5, 39, N'1637281794494.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (6, 3, N'1637281707270.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (7, 4, N'1637281717143.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (8, 5, N'1637281723604.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (9, 6, N'1637281734150.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (10, 6, N'1637281743270.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (11, 7, N'1637281753248.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (12, 7, N'1637281761699.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (13, 8, N'1637281768558.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (14, 8, N'1637281774367.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (15, 9, N'1637281493847.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (16, 10, N'1637281499064.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (17, 11, N'1637281504825.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (18, 12, N'1637281511908.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (19, 14, N'1637281521603.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (20, 22, N'1637282063257.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (21, 22, N'1637282070419.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (22, 23, N'1637282076356.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (23, 24, N'1637282081205.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (24, 25, N'1637282086806.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (25, 26, N'1637282092721.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (26, 15, N'1637279254449.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (28, 15, N'1637279262380.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (29, 16, N'1637279270684.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (30, 16, N'1637279277439.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (31, 17, N'1637279290772.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (32, 17, N'1637279300465.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (33, 18, N'1637279443240.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (34, 18, N'1637279451429.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (35, 18, N'1637279460858.png')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (36, 18, N'1637279473016.png')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (37, 19, N'1637279481754.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (38, 19, N'1637279492033.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (39, 20, N'1637279501132.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (40, 20, N'1637279510048.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (41, 20, N'1637279538230.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (42, 21, N'1637279547467.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (43, 21, N'1637279555012.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (44, 27, N'1637277569774.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (45, 27, N'1637277578932.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (46, 30, N'1637277590747.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (47, 30, N'1637277597944.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (48, 31, N'1637277628108.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (49, 31, N'1637277634888.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (50, 32, N'1637277642010.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (51, 32, N'1637277678021.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (52, 33, N'1637278264736.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (53, 33, N'1637278275076.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (54, 34, N'1637278282084.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (55, 34, N'1637278289110.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (56, 34, N'1637278311914.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (57, 35, N'1637278319816.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (58, 35, N'1637278327166.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (59, 36, N'1637278337682.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (60, 36, N'1637278347591.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (61, 38, N'1637278362193.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (63, 38, N'1637278431485.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (64, 41, N'1637282108884.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (65, 41, N'1637282102584.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (67, 41, N'1637282121979.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (68, 42, N'1637282129426.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (71, 43, N'1637279568689.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (72, 43, N'1637279584318.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (73, 43, N'1637279593458.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (74, 43, N'1638980634983.png')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (1075, 3, N'1650065409519.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (1078, 1044, N'1650065579943.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (2080, 2044, N'1650910902958.jpg')
INSERT [dbo].[ProductImage] ([id], [productId], [imagePath]) VALUES (3076, 1, N'1651100744960.jpg')
SET IDENTITY_INSERT [dbo].[ProductImage] OFF
GO
SET IDENTITY_INSERT [dbo].[ProductType] ON 

INSERT [dbo].[ProductType] ([id], [name]) VALUES (6, N'Bộ 1')
INSERT [dbo].[ProductType] ([id], [name]) VALUES (4, N'Bộ 6')
INSERT [dbo].[ProductType] ([id], [name]) VALUES (5, N'Bộ 9')
INSERT [dbo].[ProductType] ([id], [name]) VALUES (3, N'Size L')
INSERT [dbo].[ProductType] ([id], [name]) VALUES (2, N'Size M')
INSERT [dbo].[ProductType] ([id], [name]) VALUES (1, N'Size S')
INSERT [dbo].[ProductType] ([id], [name]) VALUES (8, N'Size X')
INSERT [dbo].[ProductType] ([id], [name]) VALUES (7, N'TEST')
SET IDENTITY_INSERT [dbo].[ProductType] OFF
GO
SET IDENTITY_INSERT [dbo].[ProductVariant] ON 

INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (2, 1, 3, CAST(130002 AS Decimal(9, 0)), CAST(200005 AS Decimal(9, 0)), 0, 0)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (4, 1, 4, CAST(150000 AS Decimal(9, 0)), CAST(220000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (5, 14, 1, CAST(20000 AS Decimal(9, 0)), CAST(40000 AS Decimal(9, 0)), 1, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (7, 3, 2, CAST(20000 AS Decimal(9, 0)), CAST(40000 AS Decimal(9, 0)), 1, 0)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (8, 3, 4, CAST(80000 AS Decimal(9, 0)), CAST(130000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (10, 3, 5, CAST(90000 AS Decimal(9, 0)), CAST(145000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (11, 4, 4, CAST(100000 AS Decimal(9, 0)), CAST(150000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (12, 4, 5, CAST(115000 AS Decimal(9, 0)), CAST(165000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (14, 5, 5, CAST(145000 AS Decimal(9, 0)), CAST(200000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (15, 6, 4, CAST(100000 AS Decimal(9, 0)), CAST(150000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (16, 6, 5, CAST(115000 AS Decimal(9, 0)), CAST(170000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (17, 7, 4, CAST(80000 AS Decimal(9, 0)), CAST(130000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (18, 7, 5, CAST(105000 AS Decimal(9, 0)), CAST(150000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (19, 8, 4, CAST(160000 AS Decimal(9, 0)), CAST(220000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (20, 9, 4, CAST(160000 AS Decimal(9, 0)), CAST(220000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (21, 9, 5, CAST(180000 AS Decimal(9, 0)), CAST(260000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (22, 10, 4, CAST(160000 AS Decimal(9, 0)), CAST(220000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (23, 10, 5, CAST(180000 AS Decimal(9, 0)), CAST(255000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (24, 11, 4, CAST(170000 AS Decimal(9, 0)), CAST(240000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (25, 12, 5, CAST(160000 AS Decimal(9, 0)), CAST(240000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (26, 12, 4, CAST(175000 AS Decimal(9, 0)), CAST(260000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (28, 14, 5, CAST(160000 AS Decimal(9, 0)), CAST(250000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (30, 15, 2, CAST(270000 AS Decimal(9, 0)), CAST(380000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (31, 15, 3, CAST(290000 AS Decimal(9, 0)), CAST(410000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (33, 16, 1, CAST(240000 AS Decimal(9, 0)), CAST(330000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (34, 16, 3, CAST(300000 AS Decimal(9, 0)), CAST(420000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (35, 16, 2, CAST(270000 AS Decimal(9, 0)), CAST(340000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (36, 17, 1, CAST(280000 AS Decimal(9, 0)), CAST(350000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (37, 17, 2, CAST(305000 AS Decimal(9, 0)), CAST(410000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (38, 18, 3, CAST(350000 AS Decimal(9, 0)), CAST(450000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (39, 19, 1, CAST(270000 AS Decimal(9, 0)), CAST(330000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (41, 19, 2, CAST(290000 AS Decimal(9, 0)), CAST(370000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (42, 19, 3, CAST(325000 AS Decimal(9, 0)), CAST(420000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (43, 20, 1, CAST(240000 AS Decimal(9, 0)), CAST(320000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (44, 21, 2, CAST(305000 AS Decimal(9, 0)), CAST(400000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (45, 22, 1, CAST(265000 AS Decimal(9, 0)), CAST(345000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (46, 22, 3, CAST(280000 AS Decimal(9, 0)), CAST(380000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (47, 23, 2, CAST(270000 AS Decimal(9, 0)), CAST(350000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (48, 23, 3, CAST(290000 AS Decimal(9, 0)), CAST(385000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (49, 24, 2, CAST(275000 AS Decimal(9, 0)), CAST(380000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (50, 25, 1, CAST(220000 AS Decimal(9, 0)), CAST(310000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (51, 26, 2, CAST(270000 AS Decimal(9, 0)), CAST(330000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (53, 26, 3, CAST(290000 AS Decimal(9, 0)), CAST(415000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (54, 27, 1, CAST(300000 AS Decimal(9, 0)), CAST(420000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (55, 27, 3, CAST(350000 AS Decimal(9, 0)), CAST(470000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (58, 30, 2, CAST(285000 AS Decimal(9, 0)), CAST(400000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (60, 31, 1, CAST(280000 AS Decimal(9, 0)), CAST(360000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (61, 31, 2, CAST(300000 AS Decimal(9, 0)), CAST(400000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (62, 31, 3, CAST(310000 AS Decimal(9, 0)), CAST(455000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (63, 32, 2, CAST(290000 AS Decimal(9, 0)), CAST(400000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (64, 32, 3, CAST(310000 AS Decimal(9, 0)), CAST(455000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (65, 33, 4, CAST(120000 AS Decimal(9, 0)), CAST(200000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (66, 33, 5, CAST(150000 AS Decimal(9, 0)), CAST(240000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (67, 34, 4, CAST(120000 AS Decimal(9, 0)), CAST(210000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (68, 34, 5, CAST(160000 AS Decimal(9, 0)), CAST(260000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (69, 35, 5, CAST(140000 AS Decimal(9, 0)), CAST(210000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (72, 36, 4, CAST(170000 AS Decimal(9, 0)), CAST(250000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (73, 38, 4, CAST(145000 AS Decimal(9, 0)), CAST(230000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (75, 38, 5, CAST(160000 AS Decimal(9, 0)), CAST(260000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (76, 39, 4, CAST(120000 AS Decimal(9, 0)), CAST(190000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (79, 39, 5, CAST(150000 AS Decimal(9, 0)), CAST(220000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (83, 41, 2, CAST(305000 AS Decimal(9, 0)), CAST(400000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (84, 41, 3, CAST(325000 AS Decimal(9, 0)), CAST(450000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (85, 42, 1, CAST(300000 AS Decimal(9, 0)), CAST(380000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (86, 42, 2, CAST(320000 AS Decimal(9, 0)), CAST(410000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (87, 42, 3, CAST(335000 AS Decimal(9, 0)), CAST(420000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (88, 43, 2, CAST(360000 AS Decimal(9, 0)), CAST(520000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (90, 43, 3, CAST(370000 AS Decimal(9, 0)), CAST(550000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (1092, 48, 4, CAST(20000 AS Decimal(9, 0)), CAST(60000 AS Decimal(9, 0)), 1, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (1093, 48, 3, CAST(15000 AS Decimal(9, 0)), CAST(50000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (2092, 12, 8, CAST(20000 AS Decimal(9, 0)), CAST(40000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (2093, 2, 3, CAST(10000 AS Decimal(9, 0)), CAST(20000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (2094, 2, 2, CAST(30000 AS Decimal(9, 0)), CAST(40000 AS Decimal(9, 0)), 0, 0)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (3092, 3, 3, CAST(100000 AS Decimal(9, 0)), CAST(80000000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (3093, 3, 6, CAST(100000 AS Decimal(9, 0)), CAST(80000000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (3094, 3, 8, CAST(100000 AS Decimal(9, 0)), CAST(80000000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (3095, 2044, 5, CAST(100000 AS Decimal(9, 0)), CAST(2000000 AS Decimal(9, 0)), 0, 1)
INSERT [dbo].[ProductVariant] ([id], [productId], [typeId], [cost], [price], [hot], [available]) VALUES (4092, 12, 1, CAST(20000 AS Decimal(9, 0)), CAST(40000 AS Decimal(9, 0)), 0, 1)
SET IDENTITY_INSERT [dbo].[ProductVariant] OFF
GO
SET IDENTITY_INSERT [dbo].[Status] ON 

INSERT [dbo].[Status] ([id], [name]) VALUES (1, N'Chờ duyệt')
INSERT [dbo].[Status] ([id], [name]) VALUES (3, N'Đã giao')
INSERT [dbo].[Status] ([id], [name]) VALUES (4, N'Đã hủy')
INSERT [dbo].[Status] ([id], [name]) VALUES (2, N'Đang giao')
SET IDENTITY_INSERT [dbo].[Status] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UC_UserInfoEmail]    Script Date: 27/04/2022 23:15:31 ******/
ALTER TABLE [dbo].[Account] ADD  CONSTRAINT [UC_UserInfoEmail] UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UC_CategoryName]    Script Date: 27/04/2022 23:15:31 ******/
ALTER TABLE [dbo].[Category] ADD  CONSTRAINT [UC_CategoryName] UNIQUE NONCLUSTERED 
(
	[name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UC_ProductName]    Script Date: 27/04/2022 23:15:31 ******/
ALTER TABLE [dbo].[Product] ADD  CONSTRAINT [UC_ProductName] UNIQUE NONCLUSTERED 
(
	[name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UC_ProductImagePath]    Script Date: 27/04/2022 23:15:31 ******/
ALTER TABLE [dbo].[ProductImage] ADD  CONSTRAINT [UC_ProductImagePath] UNIQUE NONCLUSTERED 
(
	[imagePath] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UC_SizeName]    Script Date: 27/04/2022 23:15:31 ******/
ALTER TABLE [dbo].[ProductType] ADD  CONSTRAINT [UC_SizeName] UNIQUE NONCLUSTERED 
(
	[name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [UC_ProductVariant]    Script Date: 27/04/2022 23:15:31 ******/
ALTER TABLE [dbo].[ProductVariant] ADD  CONSTRAINT [UC_ProductVariant] UNIQUE NONCLUSTERED 
(
	[productId] ASC,
	[typeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UC_StatusName]    Script Date: 27/04/2022 23:15:31 ******/
ALTER TABLE [dbo].[Status] ADD  CONSTRAINT [UC_StatusName] UNIQUE NONCLUSTERED 
(
	[name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Account] ADD  CONSTRAINT [DF_UserInfo_activated]  DEFAULT ((0)) FOR [activated]
GO
ALTER TABLE [dbo].[Account] ADD  DEFAULT ('en') FOR [langKey]
GO
ALTER TABLE [dbo].[Order] ADD  DEFAULT ((0)) FOR [paymentMethod]
GO
ALTER TABLE [dbo].[Order] ADD  DEFAULT ('0123456789') FOR [phone]
GO
ALTER TABLE [dbo].[Product] ADD  DEFAULT ((1)) FOR [available]
GO
ALTER TABLE [dbo].[AccountsAuthorities]  WITH CHECK ADD FOREIGN KEY([accountId])
REFERENCES [dbo].[Account] ([id])
GO
ALTER TABLE [dbo].[AccountsAuthorities]  WITH CHECK ADD FOREIGN KEY([authorityName])
REFERENCES [dbo].[Authority] ([name])
GO
ALTER TABLE [dbo].[CartDetail]  WITH CHECK ADD FOREIGN KEY([accountId])
REFERENCES [dbo].[Account] ([id])
GO
ALTER TABLE [dbo].[CartDetail]  WITH CHECK ADD FOREIGN KEY([variantId])
REFERENCES [dbo].[ProductVariant] ([id])
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_Order_Account] FOREIGN KEY([accountId])
REFERENCES [dbo].[Account] ([id])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK_Order_Account]
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_Order_Status] FOREIGN KEY([statusId])
REFERENCES [dbo].[Status] ([id])
GO
ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK_Order_Status]
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD  CONSTRAINT [FK_OderDetail_Oder] FOREIGN KEY([orderId])
REFERENCES [dbo].[Order] ([id])
GO
ALTER TABLE [dbo].[OrderDetail] CHECK CONSTRAINT [FK_OderDetail_Oder]
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD  CONSTRAINT [FK_OderDetail_Variant] FOREIGN KEY([variantId])
REFERENCES [dbo].[ProductVariant] ([id])
GO
ALTER TABLE [dbo].[OrderDetail] CHECK CONSTRAINT [FK_OderDetail_Variant]
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FK_Product_Category] FOREIGN KEY([categoryId])
REFERENCES [dbo].[Category] ([id])
GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FK_Product_Category]
GO
ALTER TABLE [dbo].[ProductImage]  WITH CHECK ADD  CONSTRAINT [FK_ProductImage_Product] FOREIGN KEY([productId])
REFERENCES [dbo].[Product] ([id])
GO
ALTER TABLE [dbo].[ProductImage] CHECK CONSTRAINT [FK_ProductImage_Product]
GO
ALTER TABLE [dbo].[ProductVariant]  WITH CHECK ADD  CONSTRAINT [FK_ProductVariant_Product] FOREIGN KEY([productId])
REFERENCES [dbo].[Product] ([id])
GO
ALTER TABLE [dbo].[ProductVariant] CHECK CONSTRAINT [FK_ProductVariant_Product]
GO
ALTER TABLE [dbo].[ProductVariant]  WITH CHECK ADD  CONSTRAINT [FK_ProductVariant_Size] FOREIGN KEY([typeId])
REFERENCES [dbo].[ProductType] ([id])
GO
ALTER TABLE [dbo].[ProductVariant] CHECK CONSTRAINT [FK_ProductVariant_Size]
GO
ALTER TABLE [dbo].[CartDetail]  WITH CHECK ADD CHECK  (([quantity]>(0)))
GO
ALTER TABLE [dbo].[ProductVariant]  WITH CHECK ADD  CONSTRAINT [CK_ProductVariantPrice] CHECK  (([price]>[cost]))
GO
ALTER TABLE [dbo].[ProductVariant] CHECK CONSTRAINT [CK_ProductVariantPrice]
GO
/****** Object:  StoredProcedure [dbo].[countAvailableProductInCategory]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE proc [dbo].[countAvailableProductInCategory](@categoryId INT) AS
select count(distinct(p.id)) from 
(select id from product where categoryId =@categoryId) p
join ProductVariant v on v.productId = p.id and v.available = 1
join ProductImage i on i.productId = p.id 
GO
/****** Object:  StoredProcedure [dbo].[getAccountOrderCount]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[getAccountOrderCount](
	@accountId AS INT,
	@statusId AS INT
)
AS
	-- tìm ra các sản phẩm với loại cần tìm và có giá nhỏ nhất
	select COUNT(*)
	from [Order] o 
	WHERE o.accountId = @accountId and o.statusId = @statusId
GO
/****** Object:  StoredProcedure [dbo].[getAccountOrderDetails]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE proc [dbo].[getAccountOrderDetails](
	@orderId INT
)
as
	select 
	Product.name as productName, 
	pType.name as [type],
	detail.quantity,
	detail.unitPrice,
	pImage.imagePath as [image] 
	from (
		select od.id
		from [Order] od
		where  id = @orderId
	) o 
	join OrderDetail detail 
		on detail.oderId = o.id
	join ProductVariant variant
		on detail.variantId = variant.id
	join Product 
		on variant.productId = Product.id
	join ProductType pType
		on variant.typeId = pType.id
	join
	 (select top 1 with ties
		productId,
		imagePath
		from productImage
		order by row_number() 
		over (partition by productId order by productId)) 
		pImage on product.id = pImage.productId;
GO
/****** Object:  StoredProcedure [dbo].[getFullDetailOfOrders]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE proc [dbo].[getFullDetailOfOrders](
	@orderId INT
)
as
	select 
	Product.name as productName, 
	pType.name as [type],
	detail.quantity,
	detail.unitPrice
	from (
		select od.id
		from [Order] od
		where  id = @orderId
	) o 
	join OrderDetail detail 
		on detail.oderId = o.id
	join ProductVariant variant
		on detail.variantId = variant.id
	join Product 
		on variant.productId = Product.id
	join ProductType pType
		on variant.typeId = pType.id;
GO
/****** Object:  StoredProcedure [dbo].[loadAccountOrderInfo]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[loadAccountOrderInfo](
	@accountId AS INT,
	@pageNumber AS INT,
	@rowsOfPage AS INT
)
AS
	-- tìm ra các sản phẩm với loại cần tìm và có giá nhỏ nhất
	select o.id, DATEADD(dd, 0, DATEDIFF(dd, 0, o.createdAt)) as [createdDate] , o.statusId, o.total
	from [Order] o 
	WHERE o.accountId = @accountId
	ORDER BY o.id 
	OFFSET (@PageNumber-1)*@RowsOfPage ROWS 
	FETCH NEXT @RowsOfPage ROWS ONLY
GO
/****** Object:  StoredProcedure [dbo].[loadAccountOrderInfoFromStatusId]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[loadAccountOrderInfoFromStatusId](
	@accountId AS INT,
	@statusId AS INT,
	@pageNumber AS INT,
	@rowsOfPage AS INT
)
AS
	-- tìm ra các sản phẩm với loại cần tìm và có giá nhỏ nhất
	select o.id, DATEADD(dd, 0, DATEDIFF(dd, 0, o.createdAt)) as [createdDate] , o.statusId, o.total
	from [Order] o 
	WHERE o.accountId = @accountId and o.statusId = @statusId
	ORDER BY o.id 
	OFFSET (@PageNumber-1)*@RowsOfPage ROWS 
	FETCH NEXT @RowsOfPage ROWS ONLY
GO
/****** Object:  StoredProcedure [dbo].[loadOrders]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROC [dbo].[loadOrders] (
	@pageNumber AS INT,
	@rowsOfPage AS INT
)
AS 
	-- lấy ra đủ số lượng order trước khi join
	SELECT * INTO #OrderAtPage
	FROM [order] o
	ORDER BY o.createdAt DESC
	OFFSET (@PageNumber-1)*@RowsOfPage ROWS 
	FETCH NEXT @RowsOfPage ROWS ONLY
	-- join
	SELECT 
		o.id, 
		a.id as accountId,
		concat(a.lastName, ' ', a.firstName) as fullName,
		o.createdAt,
		o.total,
		o.paymentMethod,
		o.statusId,
		s.name as [status],
		o.address,
		o.note
	FROM #OrderAtPage o
	JOIN [Status] s on o.statusId = s.id
	JOIN [Account] a on o.accountId = a.id
GO
/****** Object:  StoredProcedure [dbo].[loadProduct]    Script Date: 27/04/2022 23:15:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[loadProduct](
	@categoryId AS INT,
	@pageNumber AS INT,
	@rowsOfPage AS INT
)
AS
	-- tìm ra các sản phẩm với loại cần tìm và có giá nhỏ nhất
	select 
		product.id as productId,
		product.name, 
		product.ingredients, 
		productMin.price,
		pImage.imagePath as [image] 
	from (
		-- tìm ra thông tin sản phẩm đúng loại
		select * 
		from Product
		where categoryId = @categoryId and Product.available = 1
	) product 
		-- sản phẩm có giá nhỏ nhất
	join ProductWithMinPrice productMin on product.id = productMin.productId 
	-- 
	join
	 (select top 1 with ties
		productId,
		imagePath
		from productImage
		order by row_number() 
		over (partition by productId order by productId)) pImage on product.id = pImage.productId
	ORDER BY product.id 
	OFFSET (@PageNumber-1)*@RowsOfPage ROWS 
	FETCH NEXT @RowsOfPage ROWS ONLY
GO
USE [master]
GO
ALTER DATABASE [BAKERY_SHOP] SET  READ_WRITE 
GO
