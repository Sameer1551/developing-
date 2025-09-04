import React, { useState } from 'react';
import { 
  Search, 
  Filter, 
  Plus, 
  Edit, 
  Trash2, 
  Eye, 
  MoreHorizontal,
  Bell,
  AlertTriangle,
  CheckCircle,
  Clock,
  MapPin,
  User,
  MessageSquare,
  Phone,
  Mail,
  ExternalLink
} from 'lucide-react';
import CreateAlertModal from './CreateAlertModal';

interface Alert {
  id: string;
  title: string;
  description: string;
  type: 'Health Emergency' | 'Water Quality' | 'Disease Outbreak' | 'Infrastructure' | 'Weather';
  priority: 'Critical' | 'High' | 'Medium' | 'Low';
  status: 'Active' | 'In Progress' | 'Resolved' | 'Closed';
  district: string;
  location: string;
  reporter: string;
  reporterContact: string;
  createdAt: string;
  updatedAt: string;
  assignedTo: string;
  responseTime: string;
  notes: string[];
  attachments: string[];
}

const AlertManagementTab: React.FC = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [typeFilter, setTypeFilter] = useState<string>('all');
  const [priorityFilter, setPriorityFilter] = useState<string>('all');
  const [statusFilter, setStatusFilter] = useState<string>('all');
  const [selectedAlerts, setSelectedAlerts] = useState<string[]>([]);
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);

  // Mock data - replace with actual API calls
  const [alerts, setAlerts] = useState<Alert[]>([
    {
      id: '1',
      title: 'Water Contamination in Imphal East',
      description: 'Residents reporting foul smell and discolored water in multiple households. Suspected sewage contamination.',
      type: 'Water Quality',
      priority: 'Critical',
      status: 'Active',
      district: 'Imphal East',
      location: 'Lamphelpat area, near NIT campus',
      reporter: 'Dr. Rajesh Kumar',
      reporterContact: '+91 98765 43210',
      createdAt: '2024-01-15 08:30',
      updatedAt: '2024-01-15 14:20',
      assignedTo: 'Water Quality Team',
      responseTime: '2 hours',
      notes: [
        'Initial assessment completed',
        'Water samples collected for testing',
        'Temporary water supply arranged'
      ],
      attachments: ['water_sample_001.jpg', 'location_map.pdf']
    },
    {
      id: '2',
      title: 'Dengue Outbreak in Senapati',
      description: 'Multiple cases of dengue fever reported in Senapati district. Need immediate vector control measures.',
      type: 'Disease Outbreak',
      priority: 'High',
      status: 'In Progress',
      district: 'Senapati',
      location: 'Senapati town and surrounding villages',
      reporter: 'Priya Sharma',
      reporterContact: '+91 87654 32109',
      createdAt: '2024-01-14 16:45',
      updatedAt: '2024-01-15 10:15',
      assignedTo: 'Vector Control Team',
      responseTime: '4 hours',
      notes: [
        'Fogging operations initiated',
        'Public awareness campaign started',
        'Medical teams deployed'
      ],
      attachments: ['case_report.pdf', 'fogging_schedule.xlsx']
    },
    {
      id: '3',
      title: 'Hospital Power Outage',
      description: 'District hospital experiencing intermittent power supply. Affecting critical medical equipment.',
      type: 'Infrastructure',
      priority: 'High',
      status: 'Resolved',
      district: 'Bishnupur',
      location: 'Bishnupur District Hospital',
      reporter: 'Amit Patel',
      reporterContact: '+91 76543 21098',
      createdAt: '2024-01-13 22:15',
      updatedAt: '2024-01-14 06:30',
      assignedTo: 'Infrastructure Team',
      responseTime: '1 hour',
      notes: [
        'Generator backup activated',
        'Power restored at 06:30',
        'Equipment checked and functional'
      ],
      attachments: ['power_log.pdf', 'equipment_status.pdf']
    },
    {
      id: '4',
      title: 'Heavy Rainfall Alert',
      description: 'IMD warning for heavy rainfall in next 48 hours. Risk of flooding in low-lying areas.',
      type: 'Weather',
      priority: 'Medium',
      status: 'Active',
      district: 'All Districts',
      location: 'Northeast India region',
      reporter: 'Weather Department',
      reporterContact: 'weather@imd.gov.in',
      createdAt: '2024-01-15 12:00',
      updatedAt: '2024-01-15 12:00',
      assignedTo: 'Disaster Management Team',
      responseTime: '30 minutes',
      notes: [
        'Public advisory issued',
        'Emergency response teams on standby',
        'Shelter locations identified'
      ],
      attachments: ['weather_forecast.pdf', 'emergency_plan.pdf']
    },
    {
      id: '5',
      title: 'Food Poisoning Incident',
      description: 'Multiple cases of food poisoning reported after community feast. Need immediate investigation.',
      type: 'Health Emergency',
      priority: 'Critical',
      status: 'In Progress',
      district: 'Churachandpur',
      location: 'Churachandpur town market area',
      reporter: 'Dr. Meena Devi',
      reporterContact: '+91 65432 10987',
      createdAt: '2024-01-15 19:20',
      updatedAt: '2024-01-15 20:45',
      assignedTo: 'Food Safety Team',
      responseTime: '1 hour',
      notes: [
        'Food samples collected',
        'Affected individuals identified',
        'Medical treatment provided'
      ],
      attachments: ['food_sample_001.jpg', 'medical_report.pdf']
    }
  ]);

  const filteredAlerts = alerts.filter(alert => {
    const matchesSearch = alert.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         alert.description.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         alert.district.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesType = typeFilter === 'all' || alert.type === typeFilter;
    const matchesPriority = priorityFilter === 'all' || alert.priority === priorityFilter;
    const matchesStatus = statusFilter === 'all' || alert.status === statusFilter;
    
    return matchesSearch && matchesType && matchesPriority && matchesStatus;
  });

  const handleAlertSelection = (alertId: string) => {
    setSelectedAlerts(prev => 
      prev.includes(alertId) 
        ? prev.filter(id => id !== alertId)
        : [...prev, alertId]
    );
  };

  const handleSelectAll = () => {
    if (selectedAlerts.length === filteredAlerts.length) {
      setSelectedAlerts([]);
    } else {
      setSelectedAlerts(filteredAlerts.map(alert => alert.id));
    }
  };

  const handleCreateAlert = (newAlert: Alert) => {
    setAlerts(prev => [newAlert, ...prev]);
  };

  const getPriorityColor = (priority: string) => {
    switch (priority) {
      case 'Critical': return 'bg-red-100 text-red-800';
      case 'High': return 'bg-orange-100 text-orange-800';
      case 'Medium': return 'bg-yellow-100 text-yellow-800';
      case 'Low': return 'bg-green-100 text-green-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'Active': return 'bg-red-100 text-red-800';
      case 'In Progress': return 'bg-blue-100 text-blue-800';
      case 'Resolved': return 'bg-green-100 text-green-800';
      case 'Closed': return 'bg-gray-100 text-gray-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  };

  const getTypeIcon = (type: string) => {
    switch (type) {
      case 'Health Emergency': return <AlertTriangle className="h-5 w-5 text-red-600" />;
      case 'Water Quality': return <MapPin className="h-5 w-5 text-blue-600" />;
      case 'Disease Outbreak': return <Bell className="h-5 w-5 text-orange-600" />;
      case 'Infrastructure': return <User className="h-5 w-5 text-purple-600" />;
      case 'Weather': return <Clock className="h-5 w-5 text-yellow-600" />;
      default: return <Bell className="h-5 w-5 text-gray-600" />;
    }
  };

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
        <div>
          <h2 className="text-2xl font-bold text-gray-900">Alert Management</h2>
          <p className="text-gray-600">Monitor and manage health alerts, emergencies, and incidents</p>
        </div>
        <button 
          onClick={() => setIsCreateModalOpen(true)}
          className="inline-flex items-center px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 transition-colors"
        >
          <Plus className="h-4 w-4 mr-2" />
          Create Alert
        </button>
      </div>

      {/* Quick Stats */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <div className="bg-white p-4 rounded-lg border border-gray-200">
          <div className="flex items-center">
            <div className="p-2 bg-red-100 rounded-lg">
              <AlertTriangle className="h-6 w-6 text-red-600" />
            </div>
            <div className="ml-3">
              <p className="text-sm font-medium text-gray-500">Active Alerts</p>
              <p className="text-2xl font-bold text-gray-900">
                {alerts.filter(a => a.status === 'Active').length}
              </p>
            </div>
          </div>
        </div>
        
        <div className="bg-white p-4 rounded-lg border border-gray-200">
          <div className="flex items-center">
            <div className="p-2 bg-blue-100 rounded-lg">
              <Clock className="h-6 w-6 text-blue-600" />
            </div>
            <div className="ml-3">
              <p className="text-sm font-medium text-gray-500">In Progress</p>
              <p className="text-2xl font-bold text-gray-900">
                {alerts.filter(a => a.status === 'In Progress').length}
              </p>
            </div>
          </div>
        </div>
        
        <div className="bg-white p-4 rounded-lg border border-gray-200">
          <div className="flex items-center">
            <div className="p-2 bg-green-100 rounded-lg">
              <CheckCircle className="h-6 w-6 text-green-600" />
            </div>
            <div className="ml-3">
              <p className="text-sm font-medium text-gray-500">Resolved</p>
              <p className="text-2xl font-bold text-gray-900">
                {alerts.filter(a => a.status === 'Resolved').length}
              </p>
            </div>
          </div>
        </div>
        
        <div className="bg-white p-4 rounded-lg border border-gray-200">
          <div className="flex items-center">
            <div className="p-2 bg-orange-100 rounded-lg">
              <Bell className="h-6 w-6 text-orange-600" />
            </div>
            <div className="ml-3">
              <p className="text-sm font-medium text-gray-500">Critical</p>
              <p className="text-2xl font-bold text-gray-900">
                {alerts.filter(a => a.priority === 'Critical').length}
              </p>
            </div>
          </div>
        </div>
      </div>

      {/* Filters and Search */}
      <div className="bg-white p-4 rounded-lg border border-gray-200">
        <div className="flex flex-col lg:flex-row gap-4">
          <div className="flex-1">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-gray-400" />
              <input
                type="text"
                placeholder="Search alerts by title, description, or district..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              />
            </div>
          </div>
          
          <div className="flex gap-3">
            <select
              value={typeFilter}
              onChange={(e) => setTypeFilter(e.target.value)}
              className="px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
            >
              <option value="all">All Types</option>
              <option value="Health Emergency">Health Emergency</option>
              <option value="Water Quality">Water Quality</option>
              <option value="Disease Outbreak">Disease Outbreak</option>
              <option value="Infrastructure">Infrastructure</option>
              <option value="Weather">Weather</option>
            </select>
            
            <select
              value={priorityFilter}
              onChange={(e) => setPriorityFilter(e.target.value)}
              className="px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
            >
              <option value="all">All Priorities</option>
              <option value="Critical">Critical</option>
              <option value="High">High</option>
              <option value="Medium">Medium</option>
              <option value="Low">Low</option>
            </select>
            
            <select
              value={statusFilter}
              onChange={(e) => setStatusFilter(e.target.value)}
              className="px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
            >
              <option value="all">All Status</option>
              <option value="Active">Active</option>
              <option value="In Progress">In Progress</option>
              <option value="Resolved">Resolved</option>
              <option value="Closed">Closed</option>
            </select>
          </div>
        </div>
      </div>

      {/* Bulk Actions */}
      {selectedAlerts.length > 0 && (
        <div className="bg-blue-50 border border-blue-200 rounded-lg p-4">
          <div className="flex items-center justify-between">
            <span className="text-blue-800">
              {selectedAlerts.length} alert(s) selected
            </span>
            <div className="flex gap-2">
              <button className="px-3 py-1 bg-blue-600 text-white rounded hover:bg-blue-700 transition-colors">
                Mark In Progress
              </button>
              <button className="px-3 py-1 bg-green-600 text-white rounded hover:bg-green-700 transition-colors">
                Mark Resolved
              </button>
              <button className="px-3 py-1 bg-gray-600 text-white rounded hover:bg-gray-700 transition-colors">
                Close
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Alerts Table */}
      <div className="bg-white rounded-lg border border-gray-200 overflow-hidden">
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left">
                  <input
                    type="checkbox"
                    checked={selectedAlerts.length === filteredAlerts.length && filteredAlerts.length > 0}
                    onChange={handleSelectAll}
                    className="rounded border-gray-300 text-blue-600 focus:ring-blue-500"
                  />
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Alert
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Type
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Priority
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Status
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  District
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Response Time
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Actions
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {filteredAlerts.map((alert) => (
                <tr key={alert.id} className="hover:bg-gray-50">
                  <td className="px-6 py-4 whitespace-nowrap">
                    <input
                      type="checkbox"
                      checked={selectedAlerts.includes(alert.id)}
                      onChange={() => handleAlertSelection(alert.id)}
                      className="rounded border-gray-300 text-blue-600 focus:ring-blue-500"
                    />
                  </td>
                  <td className="px-6 py-4">
                    <div className="flex items-start">
                      <div className="flex-shrink-0">
                        {getTypeIcon(alert.type)}
                      </div>
                      <div className="ml-3">
                        <div className="text-sm font-medium text-gray-900">{alert.title}</div>
                        <div className="text-sm text-gray-500 line-clamp-2 max-w-md">{alert.description}</div>
                        <div className="mt-1 flex items-center text-xs text-gray-400">
                          <User className="h-3 w-3 mr-1" />
                          {alert.reporter}
                          <span className="mx-2">•</span>
                          {alert.createdAt}
                        </div>
                      </div>
                    </div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span className="inline-flex items-center px-2 py-1 text-xs font-medium rounded-full bg-gray-100 text-gray-800">
                      {alert.type}
                    </span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span className={`inline-flex px-2 py-1 text-xs font-semibold rounded-full ${getPriorityColor(alert.priority)}`}>
                      {alert.priority}
                    </span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span className={`inline-flex px-2 py-1 text-xs font-semibold rounded-full ${getStatusColor(alert.status)}`}>
                      {alert.status}
                    </span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    {alert.district}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {alert.responseTime}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                    <div className="flex items-center space-x-2">
                      <button className="text-blue-600 hover:text-blue-900" title="View Details">
                        <Eye className="h-4 w-4" />
                      </button>
                      <button className="text-indigo-600 hover:text-indigo-900" title="Edit Alert">
                        <Edit className="h-4 w-4" />
                      </button>
                      <button className="text-green-600 hover:text-green-900" title="Add Note">
                        <MessageSquare className="h-4 w-4" />
                      </button>
                      <button className="text-gray-600 hover:text-gray-900" title="More Options">
                        <MoreHorizontal className="h-4 w-4" />
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        
        {/* Pagination */}
        <div className="bg-white px-4 py-3 border-t border-gray-200 sm:px-6">
          <div className="flex items-center justify-between">
            <div className="flex-1 flex justify-between sm:hidden">
              <button className="relative inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50">
                Previous
              </button>
              <button className="ml-3 relative inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50">
                Next
              </button>
            </div>
            <div className="hidden sm:flex-1 sm:flex sm:items-center sm:justify-between">
              <div>
                <p className="text-sm text-gray-700">
                  Showing <span className="font-medium">1</span> to <span className="font-medium">{filteredAlerts.length}</span> of{' '}
                  <span className="font-medium">{alerts.length}</span> results
                </p>
              </div>
              <div>
                <nav className="relative z-0 inline-flex rounded-md shadow-sm -space-x-px">
                  <button className="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">
                    Previous
                  </button>
                  <button className="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">
                    Next
                  </button>
                </nav>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Create Alert Modal */}
      <CreateAlertModal
        isOpen={isCreateModalOpen}
        onClose={() => setIsCreateModalOpen(false)}
        onAlertCreated={handleCreateAlert}
      />
    </div>
  );
};

export default AlertManagementTab;
