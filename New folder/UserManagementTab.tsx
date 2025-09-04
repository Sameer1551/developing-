--- a/src/components/AdminDashboard/UserManagementTab.tsx
+++ b/src/components/AdminDashboard/UserManagementTab.tsx
@@ -5,22 +5,19 @@
   Edit,
   Trash2,
   Eye,
   MoreHorizontal,
   User,
   Plus
 } from 'lucide-react';
 import AddUserModal from './AddUserModal';
+import axios from 'axios';
+import { useAuth } from '../../contexts/AuthContext';
 
 interface User {
   id: string;
   name: string;
   email: string;
   phone: string;
   role: 'Admin' | 'ASHA Workers' | 'ANM' | 'Nurses' | 'Health Staff' | 'Government Officials' | 'District Health Officer';
   status: 'Active' | 'Inactive' | 'Suspended';
   district: string;
-  lastActive: string;
-  joinDate: string;
-  permissions: string[];
-  state?: string;
-  mobile?: string;
-  originalRole?: string;
+  lastActive?: string;
+  joinDate?: string;
+  permissions?: string[];
+  state?: string;
+  mobile?: string;
+  originalRole?: 'Administrator' | 'Health Worker' | 'Staff' | 'Government Officials';
 }
 
 const UserManagementTab: React.FC = () => {
   const [searchTerm, setSearchTerm] = useState('');
   const [roleFilter, setRoleFilter] = useState<string>('all');
   const [statusFilter, setStatusFilter] = useState<string>('all');
   const [selectedUsers, setSelectedUsers] = useState<string[]>([]);
-  const [allUsers, setAllUsers] = useState<User[]>([]);
+  const [users, setUsers] = useState<User[]>([]);
   const [loading, setLoading] = useState(true);
+  const [error, setError] = useState<string | null>(null);
   const [isAddUserModalOpen, setIsAddUserModalOpen] = useState(false);
+  const { token } = useAuth();
 
   // Load existing users from backend on component mount
   useEffect(() => {
     const loadUsers = async () => {
       try {
-        const response = await fetch('/DATAUAD.json');
+        if (!token) {
+          setError('Authentication token not found. Please log in.');
+          setLoading(false);
+          return;
+        }
+
+        const response = await axios.get('/api/users', {
+          headers: {
+            'Authorization': `Bearer ${token}`
+          }
+        });
+        setUsers(response.data);
+        setError(null);
+      } catch (error) {
+        console.error('Failed to load users:', error);
+        setError('Failed to fetch users. Please ensure the backend is running.');
       } finally {
         setLoading(false);
       }
     };
 
     loadUsers();
-  }, []);
-
-  // Use the allUsers state instead of empty mock data
-  const users = allUsers;
+  }, [token]);
 
   const filteredUsers = users.filter(user => {
     const matchesSearch = user.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                          user.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
-                         user.district.toLowerCase().includes(searchTerm.toLowerCase());
+                         (user.district || '').toLowerCase().includes(searchTerm.toLowerCase());
     const matchesRole = roleFilter === 'all' || user.role === roleFilter;
     const matchesStatus = statusFilter === 'all' || user.status === statusFilter;
     
@@ -62,10 +59,9 @@
     if (selectedUsers.length === filteredUsers.length) {
       setSelectedUsers([]);
     } else {
-      setSelectedUsers(filteredUsers.map(user => user.id));
+      setSelectedUsers(filteredUsers.map(user => user.id));  
     }
   };
-
   const getStatusColor = (status: string) => {
     switch (status) {
       case 'Active': return 'bg-green-100 text-green-800';
@@ -79,32 +75,11 @@
       case 'ASHA Workers': return 'bg-green-100 text-green-800';
       case 'ANM': return 'bg-blue-100 text-blue-800';
       case 'Nurses': return 'bg-indigo-100 text-indigo-800';
       case 'Health Staff': return 'bg-cyan-100 text-cyan-800';
       case 'Government Officials': return 'bg-yellow-100 text-yellow-800';
       case 'District Health Officer': return 'bg-red-100 text-red-800';
       case 'Health Officer': return 'bg-blue-100 text-blue-800';
       case 'Health Worker': return 'bg-blue-100 text-blue-800';
-      case 'Staff': return 'bg-orange-100 text-orange-800';
+      case 'Staff': return 'bg-orange-100 text-orange-800'; // Added
       case 'User': return 'bg-gray-100 text-gray-800';
       default: return 'bg-gray-100 text-gray-800';
     }
   };
-
-
-
-  const getStateFromDistrict = (district: string): string => {
-    const stateDistrictMapping: { [key: string]: string[] } = {
-      'Arunachal Pradesh': [
-        'Anjaw', 'Changlang', 'Dibang Valley', 'East Kameng', 'East Siang', 'Kamle', 'Kra Daadi', 'Kurung Kumey', 'Lepa Rada', 'Lohit', 'Longding', 'Lower Dibang Valley', 'Lower Siang', 'Lower Subansiri', 'Namsai', 'Pakke Kessang', 'Papum Pare', 'Shi Yomi', 'Siang', 'Tawang', 'Tirap', 'Upper Siang', 'Upper Subansiri', 'West Kameng', 'West Siang'
-      ],
-      'Assam': [
-        'Baksa', 'Barpeta', 'Biswanath', 'Bongaigaon', 'Cachar', 'Charaideo', 'Chirang', 'Darrang', 'Dhemaji', 'Dhubri', 'Dibrugarh', 'Dima Hasao', 'Goalpara', 'Golaghat', 'Hailakandi', 'Hojai', 'Jorhat', 'Kamrup', 'Kamrup Metropolitan', 'Karbi Anglong', 'Karimganj', 'Kokrajhar', 'Lakhimpur', 'Majuli', 'Morigaon', 'Nagaon', 'Nalbari', 'Sivasagar', 'Sonitpur', 'South Salmara-Mankachar', 'Tinsukia', 'Udalguri', 'West Karbi Anglong'
-      ],
-      'Manipur': [
-        'Bishnupur', 'Chandel', 'Churachandpur', 'Imphal East', 'Imphal West', 'Jiribam', 'Kakching', 'Kamjong', 'Kangpokpi', 'Noney', 'Pherzawl', 'Senapati', 'Tamenglong', 'Tengnoupal', 'Thoubal', 'Ukhrul'
-      ],
-      'Meghalaya': [
-        'East Garo Hills', 'East Jaintia Hills', 'East Khasi Hills', 'North Garo Hills', 'Ri Bhoi', 'South Garo Hills', 'South West Garo Hills', 'South West Khasi Hills', 'West Garo Hills', 'West Jaintia Hills', 'West Khasi Hills'
-      ],
-      'Mizoram': [
-        'Aizawl', 'Champhai', 'Hnahthial', 'Khawzawl', 'Kolasib', 'Lawngtlai', 'Lunglei', 'Mamit', 'Saiha', 'Saitual', 'Serchhip'
-      ],
-      'Nagaland': [
-        'Dimapur', 'Kiphire', 'Kohima', 'Longleng', 'Mokokchung', 'Mon', 'Peren', 'Phek', 'Tuensang', 'Wokha', 'Zunheboto'
-      ],
-      'Tripura': [
-        'Dhalai', 'Gomati', 'Khowai', 'North Tripura', 'Sipahijala', 'South Tripura', 'Unakoti', 'West Tripura'
-      ],
-      'Sikkim': [
-        'East Sikkim', 'North Sikkim', 'South Sikkim', 'West Sikkim'
-      ]
-    };
-
-    for (const [state, districts] of Object.entries(stateDistrictMapping)) {
-      if (districts.includes(district)) {
-        return state;
-      }
-    }
-    return "Unknown";
-  };
-
-  const handleAddUser = (newUser: Omit<User, 'id' | 'lastActive' | 'joinDate'>) => {
-    const userWithMetadata = {
-      ...newUser,
-      id: `user_${Date.now()}_${Math.random().toString(36).substr(2, 9)}`,
-      lastActive: new Date().toISOString(),
-      joinDate: new Date().toISOString()
-    };
-    
-    setAllUsers(prev => [...prev, userWithMetadata]);
-  };
 
   return (
     <div className="space-y-6">
@@ -148,16 +123,17 @@
         </div>
       )}
 
-      {/* Users Table */}
-      <div className="bg-white rounded-lg border border-gray-200 overflow-hidden">
+      {/* User Table */}
+      <div className="bg-white rounded-lg border border-gray-200 overflow-hidden">      
         {loading ? (
           <div className="p-8 text-center">
             <div className="inline-block animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
             <p className="mt-2 text-gray-600">Loading users...</p>
           </div>
-        ) : (
-          <>
-            <div className="overflow-x-auto">
+        ) : error ? (
+          <div className="p-8 text-center text-red-600">
+            {error}
+          </div>        
+        ) : (        
+          <>            
+            <div className="overflow-x-auto">            
               <table className="min-w-full divide-y divide-gray-200">
             <thead className="bg-gray-50">
               <tr>
@@ -219,7 +195,7 @@
                   </td>
                 </tr>
               ))}
-            </tbody>
+            </tbody>  
           </table>
         </div>
         
@@ -247,12 +223,11 @@
               </div>
             </div>
           </div>
-            </div>
-          </>
+            </div>        
+          </>        
         )}
       </div>
-
-      {/* Add User Modal */}
-      <AddUserModal
-        isOpen={isAddUserModalOpen}
-        onClose={() => setIsAddUserModalOpen(false)}
-        onAddUser={handleAddUser}
-      />
+      <AddUserModal isOpen={isAddUserModalOpen} onClose={() => setIsAddUserModalOpen(false)} />
     </div>
   );
 };