export const MENU_ITEMS: any[] = [
    {
      title: 'My Data',
      icon: 'fas fa-table',
      children: [
        {
          title: 'My Profile',
          icon: 'fas fa-id-badge',
          link: 'myprofile',
          home: true,
        }
      ],
    },
    {
      title: 'Admin Panel',
      icon: 'fas fa-users-cog',
      children: [
        {
          title: 'User Management',
          icon: 'fas fa-users',
          link: 'user/management',
          home: true,
        }
      ],
    },
    {
      title: 'Basic Data',
      icon: 'fas fa-table',
      children: [
        {
          title: 'Home',
          icon: 'fas fa-home',
          link: 'dashboard',
          home: true,
        },
        {
          title: 'Dashboard',
          icon: 'fas fa-tachometer-alt',
          link: 'dashboard',
        },
        {
          title: 'Department',
          icon: 'fas fa-building',
          link: 'department',
        },
        {
          title: 'Subject',
          icon: 'fas fa-book',
          link: 'dashboard',
        }
      ],
    }
  ];