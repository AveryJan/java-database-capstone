(() => {
const role=document.body.dataset.role;
const out=document.querySelector('#output');
let token='';
async function request(path,options={}){
 const resp=await fetch(path,{...options,headers:{'Content-Type':'application/json',...(token?{'Authorization':'Bearer '+token}:{}),...(options.headers||{})}});
 const txt=await resp.text();let val;try{val=JSON.parse(txt)}catch{val=txt}
 if(!resp.ok)throw new Error(`HTTP ${resp.status}: ${txt}`);
 return val;
}
async function list(){const path=role==='DOCTOR'?'/api/doctor/appointments':role==='PATIENT'?'/api/doctors':'/api/doctors';out.textContent=JSON.stringify(await request(path),null,2)}
document.querySelector('#sign').addEventListener('submit',async e=>{
 e.preventDefault();const f=Object.fromEntries(new FormData(e.target));
 try{const a=await request('/api/auth/login',{method:'POST',body:JSON.stringify(f)});if(a.role!==role)throw new Error('Wrong portal for this account');token=a.token;document.querySelector('#login').hidden=true;document.querySelector('#work').hidden=false;await list()}catch(err){out.textContent=err.message}
});
const add=document.querySelector('#add');if(add)add.addEventListener('submit',async e=>{e.preventDefault();try{const result=await request('/api/admin/doctors',{method:'POST',body:JSON.stringify(Object.fromEntries(new FormData(e.target)))});out.textContent='Doctor added: '+JSON.stringify(result,null,2)}catch(err){out.textContent=err.message}});
const find=document.querySelector('#find');if(find)find.addEventListener('click',async()=>{try{out.textContent=JSON.stringify(await request('/api/doctors/by-name?name='+encodeURIComponent(document.querySelector('#search').value)),null,2)}catch(err){out.textContent=err.message}});
})();
