<template>
  <sidebar-menu :menu="menu" />
</template>

<script>
export default {
  data() {
    return {
      menu: [
        {
          href: '/admin/orders',
          title: 'Order',
          icon: 'fa fa-user'
        },
        {
          href: '/',
          title: 'Batch',
          icon: 'fa fa-chart-area',
          children: [
            {
              href: '/',
              title: 'Create batch'
            },
            {
              href: '/',
              title: 'Edit batch'
            },
            {
              href: '/',
              title: 'Upload proof'
            }
          ]
        },
        {
          href: '/admin/projects',
          title: 'Project',
          icon: 'fa fa-user'
        },
        {
          href: '/',
          title: 'Client',
          icon: 'fa fa-user'
        },
      ]
    }
  }
}
</script>