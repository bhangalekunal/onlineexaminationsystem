export const MENU_ITEMS: any[] = [
    {
      title: 'Home',
      icon: 'fas fa-home',
      link: '/pages/dashboard',
      home: true,
    },
    {
      title: 'Dashboard',
      icon: 'fas fa-tachometer-alt',
      link: '/pages/iot-dashboard',
    },
    {
      title: 'Basic Data',
      icon: 'fas fa-table',
      children: [
        {
          title: 'Department',
          icon: 'fas fa-building',
          link: '/pages/layout/stepper',
        },
        {
          title: 'Subject',
          icon: 'fas fa-book',
          link: '/pages/layout/list',
        }
      ],
    }
  ];