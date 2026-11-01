(()=>{
const role=document.body.dataset.role;const storageKey='smart-clinic-'+role;let token=sessionStorage.getItem(storageKey)||'';
const el=id=>document.getElementById(id);const show=(element,on)=>element?.classList.toggle('hidden',!on);
const escapeHtml=s=>String(s??'').replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
const msg=(id,text,error=false)=>{const x=el(id);if(x){x.textContent=text;x.className='message '+(error?'error':'success');}};
async function api(path,options={}){const r=await fetch(path,{...options,headers:{'Content-Type':'application/json',...(token?{Authorization:'Bearer '+token}:{}),...(options.headers||{})}});const body=await r.json().catch(()=>({}));if(!r.ok)throw Error(body.detail||body.message||`HTTP ${r.status}`);return body;}
function signedIn(on){show(el('login'),!on);show(el('dashboard'),on);document.querySelectorAll('.logout').forEach(x=>show(x,on));if(on)refresh();}
document.querySelectorAll('.logout').forEach(b=>b.onclick=()=>{sessionStorage.removeItem(storageKey);token='';signedIn(false);});
el('loginForm').onsubmit=async e=>{e.preventDefault();const f=new FormData(e.target);try{const response=await api('/api/auth/login',{method:'POST',body:JSON.stringify({email:f.get('email'),password:f.get('password')})});if(response.role!==role)throw Error('This account is not a '+role.toLowerCase()+' account');token=response.token;sessionStorage.setItem(storageKey,token);signedIn(true);}catch(err){msg('loginMessage',err.message,true);}};
const record=d=>`<div class="record"><strong>${escapeHtml(d.name)} <small>#${escapeHtml(d.id)}</small></strong><div>${escapeHtml(d.specialty)} · ${escapeHtml(d.availableTime)}</div><small>${escapeHtml(d.email)}</small></div>`;
async function refresh(){try{
 if(role==='ADMIN')el('doctorList').innerHTML=(await api('/api/doctors')).map(record).join('')||'No doctors yet.';
 if(role==='DOCTOR')renderAppts(await api('/api/doctor/appointments'));
 if(role==='PATIENT'){renderAppts(await api('/api/patient/appointments'));el('findDoctors').dispatchEvent(new Event('submit',{cancelable:true}));}
}catch(err){msg('actionMessage',err.message,true);}}
function renderAppts(rows){el('appointments').innerHTML=rows.length?`<table><thead><tr><th>Appointment</th><th>Doctor</th><th>Patient</th><th>Status</th></tr></thead><tbody>${rows.map(a=>`<tr><td>${escapeHtml(a.appointmentTime?.replace('T',' '))}</td><td>${escapeHtml(a.doctor?.name)}</td><td>${escapeHtml(a.patient?.name)}</td><td>${escapeHtml(a.status)}</td></tr>`).join('')}</tbody></table>`:'No appointments booked.';}
if(role==='ADMIN')el('addDoctor').onsubmit=async e=>{e.preventDefault();const body=Object.fromEntries(new FormData(e.target));try{const d=await api('/api/admin/doctors',{method:'POST',body:JSON.stringify(body)});msg('actionMessage',`${d.name} was added successfully.`);e.target.reset();refresh();}catch(err){msg('actionMessage',err.message,true);}};
if(role==='PATIENT'){
 el('findDoctors').onsubmit=async e=>{e.preventDefault();const f=new FormData(e.target);try{const name=f.get('name').trim();const qs=name?'by-name?name='+encodeURIComponent(name):'search?specialty='+encodeURIComponent(f.get('specialty'))+'&time='+encodeURIComponent(f.get('time'));let rows=await api('/api/doctors/'+qs);if(name&&(f.get('specialty')||f.get('time')))rows=rows.filter(d=>d.specialty.toLowerCase().includes(String(f.get('specialty')).toLowerCase())&&d.availableTime.toLowerCase().includes(String(f.get('time')).toLowerCase()));el('doctorList').innerHTML=rows.map(record).join('')||'No matching doctors found.';}catch(err){msg('actionMessage',err.message,true);}};
 el('book').onsubmit=async e=>{e.preventDefault();const f=new FormData(e.target);try{await api('/api/patient/appointments',{method:'POST',body:JSON.stringify({doctorId:Number(f.get('doctorId')),appointmentTime:f.get('appointmentTime')})});msg('actionMessage','Appointment booked.');refresh();}catch(err){msg('actionMessage',err.message,true);}};
}
if(role==='DOCTOR')el('prescribe').onsubmit=async e=>{e.preventDefault();const f=new FormData(e.target);try{await api('/api/doctor/prescriptions',{method:'POST',body:JSON.stringify({patientId:Number(f.get('patientId')),medication:f.get('medication')})});msg('actionMessage','Prescription saved.');e.target.reset();}catch(err){msg('actionMessage',err.message,true);}};
signedIn(!!token);
})();
